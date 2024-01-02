package net.developia.mini1st.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import net.developia.mini1st.domain.UserDTO;

public interface UserService {
    void signUp(UserDTO userDTO);
    boolean isNicknameAvailable(String nickname);
	int changePwd(@Param("password") String password, @Param("email") String email);

    UserDTO getUserById(int userid);
    void updateUserProfile(UserDTO userDTO);
    
    boolean login(@Param("email")String email,@Param("password")String password);
  
    String getUserProfileImageUrl(int userId);

    boolean isEmailAvailable(String email);

    int withdrawl(String email);
}
