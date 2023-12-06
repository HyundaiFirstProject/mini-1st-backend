package net.developia.mini1st.domain;

import lombok.Data;


@Data
public class UserDTO {
    private String user_id;
    private String email;
    private String password;
    private String nickname;
}
