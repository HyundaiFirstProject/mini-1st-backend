package net.developia.mini1st.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.mapper.PetBoardMapper;

@Service
public class PetBoardServiceImpl implements PetBoardService{
	
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
		//mapper.deleteReplyCascade(bno); <- 자랑게시판 댓글 구현하면 하기로
		return mapper.deletePetBoard(bno);
	}

}
