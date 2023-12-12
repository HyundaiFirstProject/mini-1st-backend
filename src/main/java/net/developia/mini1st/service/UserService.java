package net.developia.mini1st.service;

import net.developia.mini1st.domain.UserDTO;

public interface UserService {
    void signUp(UserDTO userDTO);
    boolean isNicknameAvailable(String nickname);
	boolean checkPW(UserDTO dto);

    UserDTO getUserById(int userid);
    void updateUserProfileImage(UserDTO userDTO);

}
