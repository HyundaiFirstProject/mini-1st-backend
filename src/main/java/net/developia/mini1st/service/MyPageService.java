package net.developia.mini1st.service;

import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.UserDTO;

public interface MyPageService {

	public PetBoardDTO getBestPet(long user_no);

	public ReviewDTO getReview(long user_no);

	public UserDTO getUserInfo(String email);

}
