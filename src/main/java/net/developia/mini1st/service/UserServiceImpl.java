package net.developia.mini1st.service;

import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    @Override
    public void signUp(UserDTO userDTO){
        userMapper.insertUser(userDTO);
    }

    @Override
    public boolean isNicknameAvailable(String nickname){
       int countDuplicateNickName = userMapper.countUsersByNickname(nickname);
       if (countDuplicateNickName > 0){
           return false;
       }
       else{
           return true;
       }
    }
}
