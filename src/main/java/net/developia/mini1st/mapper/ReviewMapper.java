package net.developia.mini1st.mapper;

import java.util.List;

import net.developia.mini1st.domain.Criteria;
import net.developia.mini1st.domain.ProductsDTO;
import net.developia.mini1st.domain.ReviewBoardHeartDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.UserDTO;

public interface ReviewMapper {
	public List<ReviewDTO> getReviewList(Criteria cri);

	public int createReview(ReviewDTO dto);

	public ReviewDTO readReview(long postid);

	public int updateReview(ReviewDTO dto);

	public int deleteReview(long postid);

	public void deleteReplyCascade(long postid);

	public String getImgString(long postid);

	public UserDTO getUserInfo(long postid);

	public long getTotalCount(Criteria cri);

	public List<Long> peopleWhoLikes(long postid);

	public void increaseLikes(long postid);

	public void likesReply(ReviewBoardHeartDTO dto);

	public void decreaseLikes(long rno);

	public void likesReplyCancel(ReviewBoardHeartDTO dto);

	public ProductsDTO getProductDetail(long product_id);

	public List<ReviewDTO> searchReviews(String keyword);

	public void increaseViews(long postid);
}
