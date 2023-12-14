package net.developia.mini1st.mapper;

import java.util.List;

import net.developia.mini1st.domain.Criteria;
import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.PetBoardHeartDTO;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.domain.ReviewDTO;

public interface PetBoardMapper {

	public List<PetBoardDTO> getPetBoardList(Criteria cri);

	public int register(PetBoardDTO dto);

	public PetBoardDTO getDetail(long bno);

	public int updatePetBoard(PetBoardDTO dto);

	public int deletePetBoard(long bno);

	public List<Long> peopleWhoLikes(long bno);

	public void increaseLikes(long bno);

	public void likesReply(PetBoardHeartDTO dto);

	public void decreaseLikes(long bno);

	public void likesReplyCancel(PetBoardHeartDTO dto);

	public List<PetBoardDTO> searchPetBoards(String content);

	public void increaseViews(long bno);

	public List<UserDTO> getPeopleWhoLikes(long bno);

	public long getTotalCount(Criteria cri);

	public List<PetBoardDTO> getBestPets();

}
