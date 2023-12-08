package net.developia.mini1st.domain;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewReplyDTO {
	private long 	rno;
	private String 	writer;
	private String 	reply;
	private long	likes;
	private Date	regdate;
	private Date	updatedate;
	private long	postid;
}
