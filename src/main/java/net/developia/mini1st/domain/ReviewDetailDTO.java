package net.developia.mini1st.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewDetailDTO {
	// review_board 테이블
	private long postid;
	private List<String> img;
	private String title;
	private String content;
	private long likes;
	private String stars;
	private long views;
	private long itemid;
	// user_info 테이블
	private String nickname;
	private String img_url;
}
