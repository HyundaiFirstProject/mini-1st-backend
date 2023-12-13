package net.developia.mini1st.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.Criteria;
import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.PetBoardHeartDTO;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.mapper.PetBoardMapper;

@Service
public class PetBoardServiceImpl implements PetBoardService {

	private final PetBoardMapper mapper;

	@Autowired
	public PetBoardServiceImpl(PetBoardMapper petBoardMapper) {
		this.mapper = petBoardMapper;
	}

	@Override
	public List<PetBoardDTO> getPetBoardList() {
		return mapper.getPetBoardList();
	}

	@Override
	public int register(PetBoardDTO dto) {
		return mapper.register(dto);
	}

	@Override
	public PetBoardDTO getDetail(long bno) {
		return mapper.getDetail(bno);
	}

	@Override
	public int updatePetBoard(PetBoardDTO dto) {
		return mapper.updatePetBoard(dto);
	}

	@Override
	public int deleteReview(long bno) {
		// mapper.deleteReplyCascade(bno); <- 자랑게시판 댓글 구현하면 하기로
		return mapper.deletePetBoard(bno);
	}

	@Override
	public List<Long> peopleWhoLikes(long bno) {
		return mapper.peopleWhoLikes(bno);
	}

	@Override
	public void likesReply(PetBoardHeartDTO dto) {
		// 유저가 좋아요를 누르지 않은 상태
		// 좋아요를 1증가시키고, review_board_heart 테이블에 정보 추가(좋아요번호(시퀀스), 댓글번호, 유저번호)
		long user_no = dto.getUser_no(); // 유저 번호
		long bno = dto.getBno(); // 게시글 번호
		// 1. 좋아요 1 증가
		mapper.increaseLikes(bno);
		// 2. review_reply_heart 테이블에 추가
		mapper.likesReply(dto);

	}

	@Override
	public void likesReplyCancel(PetBoardHeartDTO dto) {
		// 유저가 좋아요를 이미 누른 상태
		// 좋아요를 1감소시키고, review_board_heart 테이블에서 정보 삭제(좋아요번호(시퀀스), 댓글번호, 유저번호)
		long bno = dto.getBno(); // 댓글 번호
		long hno = dto.getHno(); // 좋아요 번호
		// 1. 좋아요 1 감소
		mapper.decreaseLikes(bno);
		// 2. review_reply_heart 테이블에 추가
		mapper.likesReplyCancel(dto);
	}

	@Override
	public List<PetBoardDTO> searchPetBoards(String content) {
		return mapper.searchPetBoards(content);
	}

	@Override
	public void increaseViews(long bno) {
		mapper.increaseViews(bno);
	}

	@Override

	public List<UserDTO> getPeopleWhoLikes(long bno) {
		return mapper.getPeopleWhoLikes(bno);
	}

	@Override
	public long getTotalPage() {
		Criteria cri = new Criteria();
		long totalRecord = mapper.getTotalCount(cri);
		long onePageRecord = (long)cri.getAmount();
		if (totalRecord <= onePageRecord) {
			return 1;
		}
		long totalPages = (totalRecord % onePageRecord == 0) ? 
				(totalRecord / onePageRecord) 
				: (totalRecord / onePageRecord + 1);
		return totalPages;
  }

	public List<PetBoardDTO> getBestPets() {
		return mapper.getBestPets();
	}


}
