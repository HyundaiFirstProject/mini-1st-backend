package net.developia.mini1st.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.developia.mini1st.domain.Criteria;
import net.developia.mini1st.domain.ProductsDTO;
import net.developia.mini1st.domain.ReviewBoardHeartDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.ReviewDetailDTO;
import net.developia.mini1st.service.ReviewService;

@RestController
@Slf4j
@RequestMapping("/api")
public class ReviewController {
	
	@Autowired
	private ReviewService service;
	
	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.service = reviewService;
	}
	
	
	// 후기 게시판 게시글 리스트 불러오기
	@GetMapping(value="/bestReviewsList",
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ReviewDTO>> getReviewList(Criteria cri){
		try {
			List<ReviewDTO> list = service.getReviewList(cri);
			System.out.println("list=>" + list.toString());
			return new ResponseEntity<List<ReviewDTO>>(list, HttpStatus.OK);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<List<ReviewDTO>>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	// 후기 게시판 글 등록 (Create)
	@PostMapping(value="/bestReviewsPost"
				,produces = MediaType.APPLICATION_JSON_VALUE
				,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO dto){
		log.info("posting new Review....");
		System.out.println("글등록 컨트롤러 호출");
		int createCount = service.register(dto);
		log.info("## Review create count : " + createCount);
		return (createCount == 1)? 
				new ResponseEntity<ReviewDTO>(HttpStatus.OK)
				: new ResponseEntity<ReviewDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	// 후기 게시판 글 상세보기 (Read)
	@GetMapping(value="/bestReviewsDetail/{postid}"
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReviewDetailDTO> readReview(@PathVariable("postid") long postid){
		log.info("read Review : " + postid);
		System.out.println("게시판 글 상세보기(Detail) 컨트롤러 호출...");
		try {
			ReviewDetailDTO dto = service.getDetail(postid);
			return new ResponseEntity<ReviewDetailDTO>(dto, HttpStatus.OK);
		}catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<ReviewDetailDTO>(HttpStatus.NOT_FOUND);
		}
	}
	
	// 후기 게시판 글 수정(Update)
	@PostMapping(value="/bestReviewsUpdate", consumes = "application/json")
	public ResponseEntity<String> updateReview(@RequestBody ReviewDTO dto){
		log.info("update Review : " + dto.getPostid());
		// 수정 가능 항목(RequestBody) : 사진(img), 제목(title), 내용(content), 별점(star) , 제품번호(itemID)
		
		try {
	        // 해당 정보를 사용하여 업데이트 
	        int updateCount = service.updateReview(dto);
	        // 업데이트가 성공하면 OK(200) 응답을 반환
	        if (updateCount == 1) {
	            return new ResponseEntity<>("success", HttpStatus.OK);
	        } else {
	            // 업데이트가 실패하면 UNAUTHORIZED(401) 응답을 반환
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
		}catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	// 후기 게시판 게시글 삭제(Delete)
	@DeleteMapping(value="/bestReviewsDelete/{postID}"
					,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteReview(@PathVariable("postID") long postid){
		log.info("delete Review(Controller) : " + postid);
		return (service.deleteReview(postid) == 1)?
				new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		
	}
	// 후기 게시판 게시물 좋아요 
	// 1. { } 번 게시글에 좋아요를 누른 사람 목록에서 유저 검색
	// 2. 목록에서 찾지 못하면 -> 좋아요 안누른 상태 -> 좋아요 처리
	// 3. 목록에서 찾으면 -> 좋아요 누른 상태 -> 좋아요 취소 처리
	@PostMapping(value="/bestReviewsBoardLikes", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reviewBoardLikes(@RequestBody ReviewBoardHeartDTO dto){
		long postid = dto.getPostid(); // 게시글 번호
		// 1. {postid}번 게시글에 좋아요를 누른 사람 목록에서 유저가 있는지 찾는다
		List<Long> list = peopleWhoLikes(postid);
		long user_no = dto.getUser_no();
		System.out.println("---------- Controller --------");
		System.out.println(postid + " 번 게시글에 좋아요 한 사람들 목록 --");
		System.out.println(list.toString());
		System.out.println("현재 유저 번호 : " + user_no);
		System.out.println("-------------------------------");
		try {
			if (list.contains(user_no) == false) 
			{ 	// 유저가 좋아요를 누르지 않았으면
				// 좋아요를 1증가시키고, review_reply_heart 테이블에 정보 추가(좋아요번호(시퀀스), 댓글번호, 유저번호)
				service.likesReply(dto);
			}
			else if (list.contains(user_no) == true) 
			{ 	// 유저가 이미 좋아요를 누른 상태면
				// 좋아요를 1감소 시키고, review_reply_heart 테이블에 정보 삭제(좋아요번호(시퀀스), 댓글번호, 유저번호)
				service.likesReplyCancel(dto);
			}
			return new ResponseEntity<String>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	// {postid} 번 댓글을 좋아요한 유저 번호 리스트 리턴하는 메소드
	public List<Long> peopleWhoLikes(long postid){
		List<Long> list = new ArrayList<>();
		list = service.peopleWhoLikes(postid);
		return list;
	}
	
	// 후기 게시판에서 해당 아이템 조회
	@GetMapping(value="/bestReviewsItems"
				, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductsDTO> getItem(@RequestParam("product_id") long product_id){
		System.out.println("=== bestReviewsItems 컨트롤러 ===");
		try {
			ProductsDTO detail = service.getProductDetail(product_id);
			return new ResponseEntity<ProductsDTO>(detail, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ProductsDTO>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	//후기 게시판 DB 통합 검색
	@GetMapping(value="/bestReviewsBoardSearch"
				,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReviewDTO>> getSearchResult(@RequestParam("keyword") String keyword){
		System.out.println("=== DB 통합 검색 컨트롤러 ====");
		try {
			List<ReviewDTO> list = service.searchReviews(keyword);
			return new ResponseEntity<List<ReviewDTO>>(list, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ReviewDTO>>(HttpStatus.UNAUTHORIZED);
			
		}
	}

	
	
}
