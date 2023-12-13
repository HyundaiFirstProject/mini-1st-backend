package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.Criteria;
import net.developia.mini1st.domain.ProductsDTO;
import net.developia.mini1st.domain.ReviewBoardHeartDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.ReviewDetailDTO;
import net.developia.mini1st.domain.UserDTO;

public interface ReviewService {
	public List<ReviewDTO> getReviewList(Criteria cri);

	public int register(ReviewDTO dto);

	public ReviewDTO readReview(long postid);

	public int updateReview(ReviewDTO dto);

	public int deleteReview(long postid);
	
	public ReviewDetailDTO getDetail(long postid);
	
	public long getTotalCount(Criteria cri);

	public List<Long> peopleWhoLikes(long postid);

	public void likesReply(ReviewBoardHeartDTO dto);

	public void likesReplyCancel(ReviewBoardHeartDTO dto);

	public ProductsDTO getProductDetail(long product_id);

	public List<ReviewDTO> searchReviews(String keyword);

	public void increaseViews(long postid);

	public long getTotalPage();

	public List<ReviewDTO> getReviewsByItem(String product_name);

	public List<UserDTO> getPeopleWhoLikes(long postid);

	public List<ReviewDTO> getBestReview();
}
