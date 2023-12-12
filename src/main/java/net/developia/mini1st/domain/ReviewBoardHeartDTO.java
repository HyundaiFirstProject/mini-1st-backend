package net.developia.mini1st.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Getter
@Setter
@ToString
public class ReviewBoardHeartDTO {
	private long hno;		// 좋아요 번호
	private long postid;	// (좋아요할)게시글 번호
	private long user_no;	// 유저 번호
}
