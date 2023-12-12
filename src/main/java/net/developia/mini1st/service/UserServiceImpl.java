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
	public boolean checkPW(UserDTO dto) {
		int user_no = dto.getUser_no();
		String pwdRequest = dto.getPassword(); // 사용자가 입력한 비밀번호
		String pwdDB = userMapper.getUserPassword(user_no);// 데이터베이스에 저장된 비밀번호
		System.out.println("===============================");
		System.out.println("사용자가 입력한 비밀번호 : " + pwdRequest);
		System.out.println("실제 비밀번호 : " + pwdDB);
		System.out.println("일치? -> " + pwdRequest.equals(pwdDB));
		System.out.println("===============================");
		if(pwdRequest.equals(pwdDB) == true)
		{
			System.out.println("return true");
			return true;
		}
		else
		{
			System.out.println("return false");
			return false;
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

}
