package net.developia.mini1st.mapper;

import java.util.List;

import net.developia.mini1st.domain.ReviewReplyDTO;

public interface ReviewReplyMapper {

	public int createReply(ReviewReplyDTO dto);

	public List<ReviewReplyDTO> getReplyList(long postid);

}
