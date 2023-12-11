package net.developia.mini1st.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.developia.mini1st.domain.ProductsDTO;
import net.developia.mini1st.service.ProductsService;

@RestController
@RequestMapping("/api")
public class ProductsController {

	
	@Autowired
	private ProductsService service;
	
	// DB 에서 크롤링한 데이터들 불러오기
	@GetMapping(value="/bestReviewsProductList",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductsDTO>> getProductList(){
		List<ProductsDTO> list = new ArrayList<>();
		list = service.getProductList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 크롤링 수행 -> 결과 DB 저장
	@PostMapping(value="/createProductsList")
	public ResponseEntity<String> createProductsList(){
		System.out.println("createProductsList(Controller)...");
		service.createProductList();
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
