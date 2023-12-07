package net.developia.mini1st.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@GetMapping(value="/bestReviewList",
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<ReviewDTO>> getReviewList(){
		try {
			List<ReviewDTO> list = service.getReviewList();
			log.info("list=>" + list.toString());
			return new ResponseEntity<List<ReviewDTO>>(list, HttpStatus.OK);
		}catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<List<ReviewDTO>>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	// 후기 게시판 글 등록
	@PostMapping(value="/bestReviewPost"
				,produces = MediaType.APPLICATION_JSON_VALUE
				,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO dto){
		log.info("posting new Review....");
		int createCount = service.register(dto);
		log.info("## Review create count : " + createCount);
		return (createCount == 1)? 
				new ResponseEntity<ReviewDTO>(HttpStatus.OK)
				: new ResponseEntity<ReviewDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@GetMapping("/bestReviews")
	public ResponseEntity<Map<String, Object>> getBestReviews(){
		Map<String, Object> response = new HashMap<>();
		try {
			response.put("status", "200");
			response.put("description", "��ǥ �ı� �Խù�");
			response.put("data", service.getBestReview());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
            response.put("status", "500");
			response.put("description", "Internal Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
