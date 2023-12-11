package net.developia.mini1st.mapper;

import java.util.List;

import net.developia.mini1st.domain.ReviewDTO;

public interface ReviewMapper {
	public List<ReviewDTO> getReviewList();

	public int createReview(ReviewDTO dto);

	public ReviewDTO readReview(long postid);

	public int updateReview(ReviewDTO dto);

	public int deleteReview(long postid);

	public void deleteReplyCascade(long postid);

	//public long getTotalCount(PagingVO vo);

}
