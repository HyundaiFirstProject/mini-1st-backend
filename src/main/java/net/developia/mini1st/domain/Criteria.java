package net.developia.mini1st.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;

//	private String type;
//	private String keyword;
	
	public Criteria() {
		this(1, 12);
	}
	
	public Criteria(int pageNum, int amout) {
		this.pageNum = pageNum;
		this.amount = amout;
	}
}
