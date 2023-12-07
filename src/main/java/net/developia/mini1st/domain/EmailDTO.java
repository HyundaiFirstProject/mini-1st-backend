package net.developia.mini1st.domain;

import lombok.Data;

@Data
public class EmailDTO {
//DB에는 저장 안하고 오로지 이메일 인증용으로만 사용
	//mapper는 db를 관리하는 용이므로, mapper 필요 없이 바로 서비스
	Integer randNum;
	String email;
}
