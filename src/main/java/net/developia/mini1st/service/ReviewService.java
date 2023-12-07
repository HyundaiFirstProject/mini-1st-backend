package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.ReviewDTO;

public interface ReviewService {
	public List<ReviewDTO> getReviewList();

	public int register(ReviewDTO dto);
	
	public List<ReviewDTO> getBestReview();
}
