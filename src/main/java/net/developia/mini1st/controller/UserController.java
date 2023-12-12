
package net.developia.mini1st.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.EmailDTO;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.service.EmailSendService;
import net.developia.mini1st.service.UserService;

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
            // 회원가입 성공 시
            Map<String, String> response = new HashMap<>();
            response.put("status", "200");
            response.put("description", "회원가입 성공");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 회원가입 실패 시
            log.info(e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("status", "422");
            response.put("description", "회원가입 실패");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }

    @PostMapping("/checkNickname")
    public ResponseEntity<Map<String, Object>> checkNickname(@RequestBody Map<String, String> request) {
        try {
            String nickname = request.get("nickname");
            boolean isAvailable = userService.isNicknameAvailable(nickname);
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("nicknameCheck", isAvailable);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("description", "서버 오류");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
    
    @PostMapping(value="/checkPW")
    public ResponseEntity<String> checkPW(@RequestBody UserDTO dto){
    	boolean check = userService.checkPW(dto);
    	System.out.println("----- controller -----");
    	System.out.println("check -> " + check);
    	if(check == true) {
    		return new ResponseEntity<String>("success", HttpStatus.OK);
    	}else {
    		return new ResponseEntity<String>("wrong password", HttpStatus.UNAUTHORIZED);
    	}
    }
}

