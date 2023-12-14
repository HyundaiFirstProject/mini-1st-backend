package net.developia.mini1st.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
	private int user_no;
	private String email;
	private String password;
	private String nickname;
	private String img_url;
}
