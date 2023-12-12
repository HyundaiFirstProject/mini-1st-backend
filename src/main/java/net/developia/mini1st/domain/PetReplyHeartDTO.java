package net.developia.mini1st.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PetReplyHeartDTO {
	private long hno;		// 좋아요 번호
	private long rno;		// (좋아요할)댓글 번호
	private long user_no;	// 유저 번호
}
