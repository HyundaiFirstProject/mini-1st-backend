
package net.developia.mini1st.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.developia.mini1st.service.ImageS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.EmailDTO;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.security.HasRoleUser;
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
    @HasRoleUser
    public ResponseEntity<Map<String, String>> uploadProfile
            (@RequestParam("file") MultipartFile profileImage, @RequestParam Map<String, String> request) {
        try {
            Integer userId = Integer.parseInt(request.get("user_no"));
            //userId에 해당하는 UserDTO 가져오기
            UserDTO userDTO = userService.getUserById(userId);
            System.out.println("********************************");
            System.out.println(userDTO.toString());
            System.out.println("********************************");
            userDTO.setNickname(request.get("nickname"));
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
    // 비밀번호 변경 - 이메일로 변경
    @PostMapping(value="/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkPW(@RequestBody UserDTO dto){
    	System.out.println("======== 비밀번호 변경 컨트롤러 =======");
    	try {
    		// email 과 비밀번호를 보낸다
    		String password = dto.getPassword();
    		String email = dto.getEmail();
    		System.out.println("password => " + password);
    		System.out.println();
    		int result = userService.changePwd(password, email);
    		if (result == 1)
    			return new ResponseEntity<String>("success change pwd" ,HttpStatus.OK);
    		else
    			return new ResponseEntity<String>("failed", HttpStatus.BAD_REQUEST);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    	}
    }
    

    @PostMapping(value="/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String, String> request,  HttpServletRequest httpRequest, HttpServletResponse httpResponse){
    	Map<String, String> response = new HashMap<>();
    	try {
    		String email = request.get("email");
    		String password = request.get("password");
    	
    		if(userService.login(email,password)) {
    			HttpSession session = httpRequest.getSession();
    			session.setAttribute("email", email);
    			Cookie emailCookie = new Cookie("userEmail", email);
    			emailCookie.setPath("/"); // 쿠키의 유효 경로 설정, 필요에 따라 변경 가능
    			httpResponse.addCookie(emailCookie); 
    			httpResponse.setStatus(200); // HTTP 상태 설정
    			response.put("status", "200");
                response.put("description", "로그인 성공");       
                String sessionId = session.getId();
                //System.out.println("Session ID: " + sessionId);
                return ResponseEntity.ok(response);}
    		else {
    			httpResponse.setStatus(422); // HTTP 상태 설정
    			response.put("status", "422");
                response.put("description", "로그인 실패");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
            }
    	}catch(Exception e) {
    		httpResponse.setStatus(500); // HTTP 상태 설정
    		response.put("status", "500");
            response.put("description", "로그인 실패_서버 에러");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PostMapping(value="/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate(); // 세션 무효화
        return "redirect:/"; // 로그아웃 후 리다이렉트할 URL
    }
  
    @PostMapping("/singleImg")
    public ResponseEntity<String> uploadProfile
            (@RequestParam("file") MultipartFile ImageFile) {
        try {
        String responseUrl = imageS3Service.responseUrl(ImageFile);
        return new ResponseEntity<String>(responseUrl,HttpStatus.OK);
        }
        catch (Exception e) {
        log.info(e.getMessage());
        return new ResponseEntity<String>("Server error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserInfoProfile/{userno}")
    public ResponseEntity<String> getProfileUrl(@PathVariable int userno) {
        try {
            String profileUrl = userService.getUserProfileImageUrl(userno);

            // 성공한 경우
            return new ResponseEntity<>(profileUrl, HttpStatus.OK);
        }  catch (Exception e) {
            // 그 외의 예외가 발생한 경우
            log.info(e.getMessage());
            return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/checkEmail")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            boolean isAvailable = userService.isEmailAvailable(email);
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("checkEmail", isAvailable);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info(e.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("description", "서버 오류");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // 탈퇴
    @DeleteMapping("/user/withdrawl")
    public ResponseEntity<String> withdrawl(@RequestParam("email") String email){
    	System.out.println("1111");
    	try {
    		int result = userService.withdrawl(email);
    		System.out.println("## result = " + result);
    		if(result == 1) {
    			return new ResponseEntity<String>("탈퇴 성공", HttpStatus.OK);
    		}else {
    			return new ResponseEntity<String>("탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    		}
    	}catch(Exception e) {
    		System.out.println("2222");
    		log.info(e.getMessage());
    		return new ResponseEntity<String>("탈퇴 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
}
