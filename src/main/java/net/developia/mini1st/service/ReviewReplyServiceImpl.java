package net.developia.mini1st.service;

import java.util.List;

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
		// TODO Auto-generated method stub
		return mapper.getReplyList(postid);
	}

}
