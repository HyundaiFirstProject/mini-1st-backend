package net.developia.mini1st.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingVO {
	private int pageNum;
	private int	amount;
	
	private String type;
	private String keyword;
	
	public PagingVO() {
		this(1, 10);
	}
	
	public PagingVO(int pageNum, int amout) {
		this.pageNum = pageNum;
		this.amount = amout;
	}
}
