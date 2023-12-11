package net.developia.mini1st.service;

import java.util.List;

import net.developia.mini1st.domain.ProductsDTO;

public interface ProductsService {
	public void	createProductList();

	public List<ProductsDTO> getProductList();
}
