package net.developia.mini1st.controller;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.EmailDTO;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.service.EmailSendService;
import net.developia.mini1st.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Log
public class UserController {

    private final UserService userService;
    private final EmailSendService emailSendService;
    @Autowired
    public UserController(UserService userService, EmailSendService emailSendService) {
        this.userService = userService;
        this.emailSendService = emailSendService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody UserDTO userDTO) {
        try {
            userService.signUp(userDTO);
            // �쉶�썝媛��엯 �꽦怨� �떆
            Map<String, String> response = new HashMap<>();
            response.put("status", "200");
            response.put("description", "�쉶�썝媛��엯 �꽦怨�");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // �쉶�썝媛��엯 �떎�뙣 �떆
            log.info(e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("status", "422");
            response.put("description", "�쉶�썝媛��엯 �떎�뙣");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }
    
    @PostMapping("/sendEmail")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody EmailDTO emailDTO) {
        Map<String, String> response = new HashMap<>();
    	try {
    		emailSendService.sendSimpleMessage(emailDTO.getEmail(), emailDTO.getRandNum());
    		response.put("status", "200");
            response.put("description", "이메일 전송 선공");
            return ResponseEntity.ok(response);
    	}
    	catch (Exception e){
    		response.put("status", "500");
			response.put("description", "Internal Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    	}
    }
    
}
