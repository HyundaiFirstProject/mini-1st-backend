package net.developia.mini1st.domain;

import lombok.Data;

@Data
public class EmailDTO {
//DB���� ���� ���ϰ� ������ �̸��� ���������θ� ���
	//mapper�� db�� �����ϴ� ���̹Ƿ�, mapper �ʿ� ���� �ٷ� ����
	Integer randNum;
	String email;
}
