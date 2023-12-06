package net.developia.mini1st.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.service.MyPageService;

@RestController
@Log
public class MyPageController {
	
	@Autowired
	private MyPageService service;
	
	@GetMapping(value="/api/getUserInfo/{userno}",
				produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserDTO> getUserInfo(@PathVariable("userno") int user_no){
		log.info("get user info : " + user_no);
		UserDTO dto = service.getUserInfo(user_no);
		log.info("user => " + dto.toString());
		return new ResponseEntity<UserDTO>(dto, HttpStatus.OK);
	}
}
