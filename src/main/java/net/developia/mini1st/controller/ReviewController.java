package net.developia.mini1st.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.developia.mini1st.service.ReviewService;

@RestController
@Slf4j
@RequestMapping("/api")
public class ReviewController {
	
	private final ReviewService reviewService;
	
	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	@GetMapping("/bestReviews")
	public ResponseEntity<Map<String, Object>> getBestReviews(){
		Map<String, Object> response = new HashMap<>();
		try {
			response.put("status", "200");
			response.put("description", "대표 후기 게시물");
			response.put("data", reviewService.getBestReview());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
            response.put("status", "500");
			response.put("description", "Internal Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	
}
