package net.developia.mini1st.controller;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.UserDTO;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
            System.out.println(userDTO.getUser_no());
            System.out.println(userDTO.getEmail());
            System.out.println(userDTO.getNickname());
            System.out.println(userDTO.getPassword());
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

}
