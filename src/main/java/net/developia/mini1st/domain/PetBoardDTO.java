package net.developia.mini1st.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class PetBoardDTO {
	private long	bno;		// 글번호
	private	String	title;		// 제목
	private	String	content;	// 내용
	private	String	writer;		// 작성자
	private	long	views;		// 조회수
	private	long	likes;		// 좋아요
	private	String	photo;		// 사진
	private	Date	regdate;	// 작성일
	private	Date	updatedate;	// 수정일
	private	String	pettype;	// 펫 유형
	private	int	user_no;		// 유저번호
}
