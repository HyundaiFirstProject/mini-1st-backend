package net.developia.mini1st.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PetReplyDTO {
	private long 	rno;		// 댓글 번호
	private String 	writer;		// 작성자
	private String 	reply;		// 댓글 내용
	private long	likes;		// 좋아요
	private Date	regdate;	// 작성일
	private Date	updatedate;	// 수정일
	private long	bno;		// 게시글 번호(원글 번호)
}
