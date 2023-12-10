package net.developia.mini1st.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.developia.mini1st.domain.ReviewReplyDTO;
import net.developia.mini1st.domain.ReviewReplyHeartDTO;

public interface ReviewReplyService {

	public int createReply(ReviewReplyDTO dto);

	public List<ReviewReplyDTO> getReplyList(long postid);

	public int updateReply(ReviewReplyDTO dto);

	public ReviewReplyDTO getReply(long rno);

	public int deleteReply(long rno);

	public List<Long> peopleWhoLikes(long rno);

	public void likesReply(ReviewReplyHeartDTO dto);

	public void likesReplyCancel(ReviewReplyHeartDTO dto);

}