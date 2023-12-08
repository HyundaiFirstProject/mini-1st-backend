package net.developia.mini1st.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import net.developia.mini1st.domain.ReviewReplyDTO;

public interface ReviewReplyService {

	public int createReply(ReviewReplyDTO dto);

	public List<ReviewReplyDTO> getReplyList(long postid);

	public int updateReply(ReviewReplyDTO dto);

	public ReviewReplyDTO getReply(long rno);

}
