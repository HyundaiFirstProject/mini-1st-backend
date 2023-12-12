package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.PetReplyDTO;
import net.developia.mini1st.domain.PetReplyHeartDTO;
import net.developia.mini1st.domain.ReviewReplyDTO;

public interface PetReplyService {

	public int createReply(PetReplyDTO dto);

	public List<PetReplyDTO> getReplyList(long bno);

	public PetReplyDTO getReply(long rno);

	public int updateReply(PetReplyDTO newDTO);

	public int deleteReply(long rno);

	public List<Long> peopleWhoLikes(long rno);

	public void likesReply(PetReplyHeartDTO dto);

	public void likesReplyCancel(PetReplyHeartDTO dto);

}
