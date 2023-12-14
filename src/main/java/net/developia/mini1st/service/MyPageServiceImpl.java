package net.developia.mini1st.service;

import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.mapper.MyPageMapper;

@Service
public class MyPageServiceImpl implements MyPageService{

	@Autowired
	private MyPageMapper mapper;
	
	@Override
	public UserDTO getUserInfo(String email) {
		return mapper.getUserInfo(email);
	}

	@Override
	public PetBoardDTO getBestPet(long bno){
		return mapper.getBestPet(bno);}

	@Override
	public ReviewDTO getReview(long postid){
		return mapper.getReview(postid);
	}

}
