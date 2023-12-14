package net.developia.mini1st.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
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
	@GetMapping(value="/getUserInfo/{userno}",
				produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserDTO> getUserInfo(@PathVariable("userno") int user_no){
		
		log.info("get user info : " + user_no);
		try {
			UserDTO dto = service.getUserInfo(user_no);
			dto.setPassword(null);
			log.info("user => " + dto.toString());
			return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
		}catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<UserDTO>(HttpStatus.UNAUTHORIZED);
		}
	}

	@HasRoleUser
	@GetMapping(value = "/mypage/bestPetsBoard/{bno}",
			produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<String> myBestPets(@PathVariable("bno") long bno) {
		try {
			PetBoardDTO dto = service.getBestPet(bno);
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(dto);
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@HasRoleUser
	@GetMapping(value = "/mypage/bestPetsLiked/{bno}",
			produces= {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<String> myBestPetsLiked(@PathVariable("bno") long bno){
		try {
			PetBoardDTO dto = service.getBestPet(bno);
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(dto);
			return new ResponseEntity<>(json,HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

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



