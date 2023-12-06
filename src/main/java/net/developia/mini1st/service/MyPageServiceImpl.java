package net.developia.mini1st.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.mapper.MyPageMapper;

@Service
public class MyPageServiceImpl implements MyPageService{

	@Autowired
	private MyPageMapper mapper;
	
	@Override
	public UserDTO getUserInfo(int user_no) {
		return mapper.getUserInfo(user_no);
	}

}
