package net.developia.mini1st.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.service.PetBoardService;

@RestController
@RequestMapping("/api")
public class PetBoardController {
	
	@Autowired
	private PetBoardService service;
	
	@Autowired
	public PetBoardController(PetBoardService service) {
		this.service = service;
	}
	
	// 자랑게시판 게시물리스트
	@GetMapping(value="/bestPetsBoard"
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PetBoardDTO>> getPetBoardList(){
		try {
			List<PetBoardDTO> list = service.getPetBoardList();
			return new ResponseEntity<>(list, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 자랑게시판 게시글 등록
	@PostMapping(value="/bestPetsPost"
				,produces = MediaType.APPLICATION_JSON_VALUE
				,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PetBoardDTO> register(@RequestBody PetBoardDTO dto){
		System.out.println("== 자랑게시판 글 등록 컨트롤러 호출 ==");
		int createCount = service.register(dto);
		return (createCount == 1)? 
				new ResponseEntity<PetBoardDTO>(HttpStatus.OK)
				: new ResponseEntity<PetBoardDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	// 자랑게시판 게시글 상세보기
	@GetMapping(value="/bestPetsDetail"
				,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PetBoardDTO> getDetail(@RequestParam("bno")long bno){
		System.out.println("=== 자랑 게시판 글 상세보기 호출(컨트롤러) ===");
		try {
			PetBoardDTO dto = service.getDetail(bno);
			return new ResponseEntity<PetBoardDTO>(dto, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<PetBoardDTO>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	// 자랑게시판 게시글 수정
	@PostMapping(value="/bestPetsUpdate",
				consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatePetBoard(@RequestBody PetBoardDTO dto){
		try {
			int updateCount = service.updatePetBoard(dto);
			if (updateCount == 1) {
	            return new ResponseEntity<>("success", HttpStatus.OK);
	        } else {
	            // 업데이트가 실패하면 UNAUTHORIZED(401) 응답을 반환
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
		}catch(Exception e) {
			e.printStackTrace();
			return	new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	// 자랑게시판 게시글 삭제
	@DeleteMapping("/bestPetsDelete/{bno}")
	public ResponseEntity<String> deletePetBoard(@PathVariable("bno")long bno){
		System.out.println("delete PetBoard(Controller) : " + bno);
		return (service.deleteReview(bno) == 1)?
				new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
	
	// 자랑게시판 게시글 좋아요
	
	// 자랑게시판 통합 검색
}
