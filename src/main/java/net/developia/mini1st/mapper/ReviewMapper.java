package net.developia.mini1st.mapper;

import java.util.List;

import net.developia.mini1st.domain.ReviewDTO;

public interface ReviewMapper {
	public List<ReviewDTO> getReviewList();

	public int createReview(ReviewDTO dto);
}
