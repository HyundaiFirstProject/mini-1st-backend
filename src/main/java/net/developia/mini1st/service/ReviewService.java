package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.PagingVO;
import net.developia.mini1st.domain.ReviewDTO;

public interface ReviewService {
	public List<ReviewDTO> getReviewList();

	public int register(ReviewDTO dto);

	public ReviewDTO readReview(long postid);

	public int updateReview(ReviewDTO dto);

	public int deleteReview(long postid);
	
	public long getTotalCount(PagingVO vo);
}
