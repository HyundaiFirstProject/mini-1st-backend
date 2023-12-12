package net.developia.mini1st.mapper;

import java.util.List;

import net.developia.mini1st.domain.PetBoardDTO;

public interface PetBoardMapper {

	public List<PetBoardDTO> getPetBoardList();

	public int register(PetBoardDTO dto);

	public PetBoardDTO getDetail(long bno);

	public int updatePetBoard(PetBoardDTO dto);

	public int deletePetBoard(long bno);

}
