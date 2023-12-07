package net.developia.mini1st.domain;

import lombok.Data;

@Data
public class EmailDTO {
//DB와 통신X - mapper 필요 없음
	Integer randNum;
	String email;
}
