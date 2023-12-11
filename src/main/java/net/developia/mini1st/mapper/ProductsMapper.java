package net.developia.mini1st.mapper;

import java.util.List;

import net.developia.mini1st.domain.ProductsDTO;

public interface ProductsMapper {
	public int insertProduct(ProductsDTO dto);

	public void initProducts();

	public List<ProductsDTO> getProductsList();
	
	public void initSequence();
}
