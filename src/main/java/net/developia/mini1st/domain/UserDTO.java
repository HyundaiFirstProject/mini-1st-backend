package net.developia.mini1st.domain;

import lombok.Data;

@Data
public class UserDTO {
	private int user_no;
	private String email;
	private String password;
	private String nickname;
	private String img_url;
}
