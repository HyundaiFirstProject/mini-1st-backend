package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.ReviewReplyDTO;

public interface ReviewReplyService {

	int createReply(ReviewReplyDTO dto);

	List<ReviewReplyDTO> getReplyList(long postid);

}
