package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.PetBoardHeartDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.UserDTO;

public interface PetBoardService {

	List<PetBoardDTO> getPetBoardList();

	int register(PetBoardDTO dto);

	PetBoardDTO getDetail(long bno);

	int updatePetBoard(PetBoardDTO dto);

	int deleteReview(long bno);

	List<Long> peopleWhoLikes(long bno);

	void likesReply(PetBoardHeartDTO dto);

	void likesReplyCancel(PetBoardHeartDTO dto);

	List<PetBoardDTO> searchPetBoards(String content);

	void increaseViews(long bno);

	List<UserDTO> getPeopleWhoLikes(long bno);

	long getTotalPage();


	public List<PetBoardDTO> getBestPets();

}
