package net.developia.mini1st.service;

import java.util.List;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.ReviewReplyDTO;
import net.developia.mini1st.mapper.ReviewReplyMapper;
@Service
public class ReviewReplyServiceImpl implements ReviewReplyService{

	@Autowired
	private ReviewReplyMapper mapper;
	@Override
	public int createReply(ReviewReplyDTO dto) {
		System.out.println("service/register 호출.....");
		return mapper.createReply(dto);
	}
	@Override
	public List<ReviewReplyDTO> getReplyList(long postid) {
		return mapper.getReplyList(postid);
	}
	@Override
	public int updateReply(ReviewReplyDTO dto) {
		return mapper.updateReply(dto);
	}
	@Override
	public ReviewReplyDTO getReply(long rno) {
		return mapper.getReply(rno);
	}

}
