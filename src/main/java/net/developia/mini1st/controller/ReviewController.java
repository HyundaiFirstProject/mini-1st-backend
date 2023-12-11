package net.developia.mini1st.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import net.developia.mini1st.domain.ReviewDTO;
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
	
	
	// 후기 게시판 게시글 리스트 불러오기(아직 페이징 X)
	@GetMapping(value="/bestReviewsList",
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ReviewDTO>> getReviewList(){
		try {
			List<ReviewDTO> list = service.getReviewList();
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
	public ResponseEntity<ReviewDTO> readReview(@PathVariable("postid") long postid){
		log.info("read Review : " + postid);
		System.out.println("게시판 글 상세보기(Detail) 컨트롤러 호출...");
		try {
			ReviewDTO dto = service.readReview(postid);
			return new ResponseEntity<ReviewDTO>(dto, HttpStatus.OK);
		}catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<ReviewDTO>(HttpStatus.NOT_FOUND);
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

}
