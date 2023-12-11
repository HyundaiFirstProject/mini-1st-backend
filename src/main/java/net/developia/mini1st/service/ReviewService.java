package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.ReviewDetailDTO;

public interface ReviewService {
	public List<ReviewDTO> getReviewList();

	public int register(ReviewDTO dto);

	public ReviewDTO readReview(long postid);

	public int updateReview(ReviewDTO dto);

	public int deleteReview(long postid);
	
	public ReviewDetailDTO getDetail(long postid);
//	public long getTotalCount(PagingVO vo);
}
