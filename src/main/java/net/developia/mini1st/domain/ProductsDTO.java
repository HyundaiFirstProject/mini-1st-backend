package net.developia.mini1st.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ProductsDTO {
	private	long	product_id;
	private	String	product_name;
	private String	url;
	private String	img;
}
