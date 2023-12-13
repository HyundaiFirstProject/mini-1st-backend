package net.developia.mini1st.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.ReviewReplyDTO;
import net.developia.mini1st.domain.ReviewReplyHeartDTO;
import net.developia.mini1st.domain.UserDTO;
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
	@Override
	public int deleteReply(long rno) {
		return mapper.deleteReply(rno);
	}
	@Override
	public List<Long> peopleWhoLikes(long rno) {
		return mapper.peopleWhoLikes(rno);
	}
	@Override
	public void likesReply(ReviewReplyHeartDTO dto) {
		// 유저가 좋아요를 누르지 않은 상태
		// 좋아요를 1증가시키고, review_reply_heart 테이블에 정보 추가(좋아요번호(시퀀스), 댓글번호, 유저번호)
		long user_no = dto.getUser_no();	// 유저 번호
		long rno = dto.getRno();			// 댓글 번호
		System.out.println("======= service ==========");
		System.out.println("user_no = " + user_no);
		System.out.println("rno = " + rno);
		System.out.println("==========================");
		// 1. 좋아요 1 증가
		mapper.increaseLikes(rno);
		// 2. review_reply_heart 테이블에 추가
		mapper.likesReply(dto);
	}
	@Override
	public void likesReplyCancel(ReviewReplyHeartDTO dto) {
		// 유저가 좋아요를 이미 누른 상태
		// 좋아요를 1감소시키고, review_reply_heart 테이블에서 정보 삭제(좋아요번호(시퀀스), 댓글번호, 유저번호)
		long rno = dto.getRno();	// 댓글 번호
		long hno = dto.getHno();	// 좋아요 번호	
		System.out.println("*********** service(좋아요취소) *********");
		System.out.println("rno = " + rno);
		System.out.println("hno = " + hno);
		System.out.println("*************************************");
		// 1. 좋아요 1 감소
		mapper.decreaseLikes(rno);
		// 2. review_reply_heart 테이블에 추가
		mapper.likesReplyCancel(dto);
	}
	
	// rno 번 댓글 좋아요 누른 사람들의 List<UserDTO>
	@Override
	public List<UserDTO> getPeopleWhoLikes(long rno) {
		return mapper.getPeopleWhoLikes(rno);
	}

}