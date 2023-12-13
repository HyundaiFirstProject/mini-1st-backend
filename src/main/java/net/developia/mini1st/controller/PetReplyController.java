package net.developia.mini1st.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.PetReplyDTO;
import net.developia.mini1st.domain.PetReplyHeartDTO;
import net.developia.mini1st.domain.ReviewReplyDTO;
import net.developia.mini1st.domain.ReviewReplyHeartDTO;
import net.developia.mini1st.service.PetReplyService;

@RestController
@RequestMapping("/api")
@Log
public class PetReplyController {

	@Autowired
	private PetReplyService service;

	// 자랑게시판 댓글 등록
	@PostMapping(value = "/bestPetsCommentsUpload", consumes = "application/json")
	public ResponseEntity<PetReplyDTO> createReply(@RequestBody PetReplyDTO dto) {
		log.info("create Reply : " + dto);
		System.out.println("========= Controller start ========");
		System.out.println("컨트롤러 createReply 호출....");
		System.out.println("PetReplyDTO -> " + dto);
		System.out.println("========= Controller end ========");
		int createCount = service.createReply(dto);
		return (createCount == 1) ? new ResponseEntity<PetReplyDTO>(HttpStatus.OK)
				: new ResponseEntity<PetReplyDTO>(HttpStatus.UNAUTHORIZED);
	}

	// 자랑게시판 댓글 조회(댓글 리스트)
	@GetMapping(value = "/bestPetsComments/{bno}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PetReplyDTO>> getReplyList(@PathVariable("bno") long bno) {
		try {
			List<PetReplyDTO> list = service.getReplyList(bno);
			return new ResponseEntity<List<PetReplyDTO>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Exception occured... => " + e.getMessage());
			return new ResponseEntity<List<PetReplyDTO>>(HttpStatus.UNAUTHORIZED);
		}
	}

	// 자랑게시판 댓글 수정
	@PostMapping(value = "/bestPetsCommentsUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateReply(@RequestBody PetReplyDTO dto) {
		long rno = dto.getRno();
		PetReplyDTO newDTO = service.getReply(rno);
		String reply = dto.getReply();
		newDTO.setReply(reply); // 댓글 내용 수정
		return (service.updateReply(newDTO) == 1) ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	// 자랑게시판 댓글 삭제
	@DeleteMapping("/bestPetsCommentsDelete/{rno}")
	public ResponseEntity<String> deleteReply(@PathVariable("rno") long rno) {
		System.out.println("delete Reply(Controller) : " + rno);
		return (service.deleteReply(rno) == 1) ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	// 자랑게시판 댓글 좋아요
	// 좋아요 처리
	// 1. { } 번 댓글에 좋아요를 누른 사람 목록에서 유저 검색
	// 2. 목록에서 찾지 못하면 -> 좋아요 안누른 상태 -> 좋아요 처리
	// 3. 목록에서 찾으면 -> 좋아요 누른 상태 -> 좋아요 취소 처리
	@PostMapping(value = "/bestPetsCommentsLikes", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> replyLikes(@RequestBody PetReplyHeartDTO dto) {
		long rno = dto.getRno(); // 댓글 번호
		// 1. {rno} 번 댓글에 좋아요를 누른 사람 목록에서 유저가 있는지 찾는다
		List<Long> list = peopleWhoLikes(rno);
		long user_no = dto.getUser_no();
		System.out.println("---------- Controller --------");
		System.out.println(rno + " 번 좋아요 한 사람들 목록 --");
		System.out.println(list.toString());
		System.out.println("현재 유저 번호 : " + user_no);
		System.out.println("-------------------------------");
		try {
			if (list.contains(user_no) == false) {
				// 유저가 좋아요를 누르지 않았으면
				// 좋아요를 1증가시키고, pet_reply_heart 테이블에 정보 추가(좋아요번호(시퀀스), 댓글번호,유저번호)
				service.likesReply(dto);
			} else if (list.contains(user_no) == true) { 
				// 유저가 이미 좋아요를 누른 상태면
				// 좋아요를 1감소 시키고, review_reply_heart 테이블에 정보 삭제(좋아요번호(시퀀스),댓글번호, 유저번호)
				service.likesReplyCancel(dto);
			}
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
	}

	// {rno} 번 댓글을 좋아요한 유저 번호 리스트 리턴하는 메소드
	public List<Long> peopleWhoLikes(long rno) {
		List<Long> list = new ArrayList<>();
		list = service.peopleWhoLikes(rno);
		return list;
	}
}
