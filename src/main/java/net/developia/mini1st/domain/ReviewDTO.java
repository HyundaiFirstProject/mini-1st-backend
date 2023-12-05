package net.developia.mini1st.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDTO {
	private	long 	bno;		// 글 번호
	private	String	title;		// 제목
	private	String	content;	// 글 내용
	private	String	writer;		// 작성자
	private	long	readcount;	// 조회수
	private	long	likes;		// 좋아요
	private long	user_no;	// 사용자 번호
	/***************************/
	private String	photo;		// 사진 -> 데이터 타입?
	private String	regdate;	// 작성일 -> 데이터 타입 String / Date ?
	private	String	updatedate;	// 수정일 -> 데이터 타입 String / Date ?
	
}
