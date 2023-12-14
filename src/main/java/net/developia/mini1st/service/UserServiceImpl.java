package net.developia.mini1st.service;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
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

    // 비밀번호 변경
	@Override
	public int changePwd(@Param("password") String password,@Param("email") String email) {
		System.out.println("****** 비밀번호 변경 서비스 **********");
		System.out.printf("password : %s || email : %s\n", password, email);
		System.out.println("********************************");
		return userMapper.changePwd(password, email);
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
			System.out.println("service : userDTO => " + userDTO);
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


	@Override
	public boolean login(@Param("email")String email, @Param("password")String password) {
		try {
            System.out.println("email -> " + email);
            System.out.println("password -> " +password);
			int num = userMapper.login(email,password);

			if(num == 1) return true;
			else return false;
			
		}catch(Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public String getUserProfileImageUrl(int userId) {
		return userMapper.getUserProfileImageUrl(userId);
	}


	@Override
	public boolean isEmailAvailable(String email) {
		int emailCounter = userMapper.countEmail(email);
        if (emailCounter > 0){
            return false;
        }
        else{
            return true;
        }
	}
}
