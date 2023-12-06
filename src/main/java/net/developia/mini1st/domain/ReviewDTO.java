package net.developia.mini1st.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDTO {
	private long	postid;		//게시글번호
	private String	title;		//제목
	private String	content;	//내용
	private String	writer;		//작성자
	private	long	view;		//조회수
	private	long	likes;		//좋아요
	private	String	img;		//사진URL
	private Date	regdate;	//작성일
	private	Date	updatedate;	//수정일
	private	String	stars;		//별점
	private	long	user_no;	//작성자번호
	private	long	itemid;		//아이템번호
}
