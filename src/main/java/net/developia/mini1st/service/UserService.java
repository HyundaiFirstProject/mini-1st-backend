package net.developia.mini1st.service;

import net.developia.mini1st.domain.UserDTO;

public interface UserService {
    void signUp(UserDTO userDTO);
    boolean isNicknameAvailable(String nickname);
    UserDTO getUserById(int userid);
    void updateUserProfile(UserDTO userDTO);

}
