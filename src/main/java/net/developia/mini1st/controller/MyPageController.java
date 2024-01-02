package net.developia.mini1st.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.security.HasRoleUser;
import net.developia.mini1st.service.MyPageService;

@RestController
@RequestMapping("/api")
@Log
public class MyPageController {
	//깃허브 테스트
	@Autowired
	private MyPageService service;
	
	@HasRoleUser
	@GetMapping(value="/getUserInfo",
				produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserDTO> getUserInfo(@RequestParam("email") String email){
		// 이메일에서 @ 를 언더바(_) 로 바꿔서 보낼 예정
		// 여기서 다시 @ 로 바꿔 진행
		email = email.replace('_', '@');
		try {
			UserDTO dto = service.getUserInfo(email);
			dto.setPassword(null);
			System.out.println("user => " + dto.toString());
			return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
		}catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
		}
	}

	@HasRoleUser
	@GetMapping(value = "/mypage/bestPetsBoard/{user_no}",
			produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<String> myBestPets(@PathVariable("user_no") long user_no) {
		try {
			PetBoardDTO dto = service.getBestPet(user_no);
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(dto);
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// 작성한 자랑 게시판 
//	@HasRoleUser//	@GetMapping(value = "/mypage/bestPetsLiked/{user_no}",
//			produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
//	public ResponseEntity<List<PetBoardDTO>> myBestPetsLiked(@PathVariable("user_no") long user_no){
//		try {
//			List<PetBoardDTO> dto = service.getBestPet(user_no);
//			return new ResponseEntity<>(dto,HttpStatus.OK);
//		}catch (Exception e){
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}


	@HasRoleUser
	@GetMapping(value = "/mypage/bestReviewsLiked/{postid}",
			produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<String> reviewBoard(@PathVariable("postid") long postid){
		try {
			ReviewDTO dto = service.getReview(postid);
			System.out.println("dto = " + dto);
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(dto);
			return new ResponseEntity<>(json,HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}



