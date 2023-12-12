
package net.developia.mini1st.controller;

import java.util.HashMap;
import java.util.Map;

import net.developia.mini1st.service.ImageS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.EmailDTO;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.service.EmailSendService;
import net.developia.mini1st.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Log
public class UserController {

    private final UserService userService;
    private final EmailSendService emailSendService;
    private final ImageS3Service imageS3Service;

    @Autowired
    public UserController(UserService userService, EmailSendService emailSendService, ImageS3Service imageS3Service) {
        this.userService = userService;
        this.emailSendService = emailSendService;
        this.imageS3Service =  imageS3Service;
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
    @PostMapping("/user/modifyUserInfo")
    public ResponseEntity<Map<String, String>> uploadProfileImage
            (@RequestParam("file") MultipartFile profileImage, @RequestParam Map<String, String> request) {
        try {
            Integer userId = Integer.parseInt(request.get("user_no"));
            //userId에 해당하는 UserDTO 가져오기
            UserDTO userDTO = userService.getUserById(userId);
            System.out.println("userId에 해당하는 UserDTO = " + userDTO);

            imageS3Service.uploadProfile(profileImage, userDTO);


            // 이미지 업로드 성공 시
            Map<String, String> response = new HashMap<>();
            response.put("status", "200");
            response.put("description", "프로필 이미지 업로드 성공");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 실패 시
            log.info(e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("status", "422");
            response.put("description", "프로필 이미지 업로드 실패");
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
