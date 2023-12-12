package net.developia.mini1st.service;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Log
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

    @Override
    public UserDTO getUserById(int userid){
        UserDTO userDTO = userMapper.getUserById(userid);
        return userDTO;
    }
    @Override
    @Transactional
    public void updateUserProfile(UserDTO userDTO) {
        try {
            userMapper.updateUserProfile(userDTO);
        } catch (Exception e) {
            // 예외를 적절하게 처리하거나 로깅합니다.
            log.info(e.getMessage());
            e.printStackTrace();
            // 트랜잭션 롤백
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException("Failed to update user profile image", e);
        }
    }
}
