package net.developia.mini1st.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.PetReplyDTO;
import net.developia.mini1st.domain.PetReplyHeartDTO;
import net.developia.mini1st.domain.ReviewReplyDTO;
import net.developia.mini1st.mapper.PetReplyMapper;

@Service
public class PetReplyServiceImpl implements PetReplyService {

	@Autowired
	private PetReplyMapper mapper;

	@Override
	public int createReply(PetReplyDTO dto) {
		return mapper.createReply(dto);
	}

	@Override
	public List<PetReplyDTO> getReplyList(long bno) {
		return mapper.getReplyList(bno);
	}

	@Override
	public PetReplyDTO getReply(long rno) {
		return mapper.getReply(rno);
	}

	@Override
	public int updateReply(PetReplyDTO newDTO) {
		return mapper.updateReply(newDTO);
	}

	@Override
	public int deleteReply(long rno) {
		return mapper.deleteReply(rno);
	}

	@Override
	public List<Long> peopleWhoLikes(long rno) {
		return mapper.peopleWhoLikes(rno);
	}

	@Override
	public void likesReply(PetReplyHeartDTO dto) {
		// 유저가 좋아요를 누르지 않은 상태
		// 좋아요를 1증가시키고, review_reply_heart 테이블에 정보 추가(좋아요번호(시퀀스), 댓글번호, 유저번호)
		long user_no = dto.getUser_no(); // 유저 번호
		long rno = dto.getRno(); // 댓글 번호
		// 1. 좋아요 1 증가
		mapper.increaseLikes(rno);
		// 2. review_reply_heart 테이블에 추가
		mapper.likesReply(dto);

	}

	@Override
	public void likesReplyCancel(PetReplyHeartDTO dto) {
		// 유저가 좋아요를 이미 누른 상태
		// 좋아요를 1감소시키고, review_reply_heart 테이블에서 정보 삭제(좋아요번호(시퀀스), 댓글번호, 유저번호)
		long rno = dto.getRno();	// 댓글 번호
		long hno = dto.getHno();	// 좋아요 번호	
		// 1. 좋아요 1 감소
		mapper.decreaseLikes(rno);
		// 2. review_reply_heart 테이블에 추가
		mapper.likesReplyCancel(dto);
	}

}
