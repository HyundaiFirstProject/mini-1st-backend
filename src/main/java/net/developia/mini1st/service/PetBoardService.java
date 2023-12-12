package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.PetBoardDTO;

public interface PetBoardService {

	List<PetBoardDTO> getPetBoardList();

	int register(PetBoardDTO dto);

	PetBoardDTO getDetail(long bno);

	int updatePetBoard(PetBoardDTO dto);

	int deleteReview(long bno);

}
