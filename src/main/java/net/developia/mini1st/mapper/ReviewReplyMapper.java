package net.developia.mini1st.mapper;

import java.util.List;

import net.developia.mini1st.domain.ReviewReplyDTO;
import net.developia.mini1st.domain.ReviewReplyHeartDTO;
import net.developia.mini1st.domain.UserDTO;

public interface ReviewReplyMapper {

	public int createReply(ReviewReplyDTO dto);

	public List<ReviewReplyDTO> getReplyList(long postid);

	public int updateReply(ReviewReplyDTO dto);

	public ReviewReplyDTO getReply(long rno);

	public int deleteReply(long rno);

	public List<Long> peopleWhoLikes(long rno);

	public void likesReply(ReviewReplyHeartDTO dto);

	public void increaseLikes(long rno);

	public void likesReplyCancel(ReviewReplyHeartDTO dto);

	public void decreaseLikes(long rno);

	public List<UserDTO> getPeopleWhoLikes(long rno);

}