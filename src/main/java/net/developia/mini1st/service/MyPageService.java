package net.developia.mini1st.service;

import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.UserDTO;

public interface MyPageService {

	public PetBoardDTO getBestPet(long bno);

	public ReviewDTO getReview(long postid);

	public UserDTO getUserInfo(String email);

}
