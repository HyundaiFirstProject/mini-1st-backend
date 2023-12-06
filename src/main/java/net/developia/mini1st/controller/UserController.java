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
            Map<String, String> response = new HashMap<>();
            response.put("status", "422");
            response.put("description", "회원가입 실패");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }
}
