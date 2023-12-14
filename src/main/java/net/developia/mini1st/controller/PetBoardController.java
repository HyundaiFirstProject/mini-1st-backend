package net.developia.mini1st.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.*;
import net.developia.mini1st.service.ImageS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.developia.mini1st.security.HasRoleUser;
import net.developia.mini1st.service.PetBoardService;

import net.developia.mini1st.service.PetBoardService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Log
public class PetBoardController {

	private final PetBoardService service;

	private final ImageS3Service imageS3Service;

	@Autowired
	public PetBoardController(PetBoardService service, ImageS3Service imageS3Service) {
		this.service = service;
		this.imageS3Service = imageS3Service;
	}

	// 자랑게시판 게시물리스트
	@GetMapping(value = "/bestPetsBoard", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PetBoardDTO>> getPetBoardList() {
		System.out.println("== 자랑게시판 리스트 컨트롤러 호출 ==");
		try {
			List<PetBoardDTO> list = service.getPetBoardList();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 자랑게시판 게시글 등록
	@HasRoleUser
	@PostMapping(value = "/bestPetsPost", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PetBoardDTO> register(@RequestBody PetBoardDTO dto) {
		System.out.println("== 자랑게시판 글 등록 컨트롤러 호출 ==");
		System.out.println("** content : " + dto.getContent());
		System.out.println("==============================");
		int createCount = service.insertPetBoard(dto);
		return (createCount == 1) ? new ResponseEntity<PetBoardDTO>(HttpStatus.OK)
				: new ResponseEntity<PetBoardDTO>(HttpStatus.UNAUTHORIZED);
	}

	// 자랑게시판 게시글 상세보기
	@GetMapping(value = "/bestPetsDetail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PetBoardDTO> getDetail(@RequestParam("bno") long bno) {
		System.out.println("=== 자랑 게시판 글 상세보기 호출(컨트롤러) ===");
		service.increaseViews(bno); // 조회수 1증가
		try {
			PetBoardDTO dto = service.getDetail(bno);
			return new ResponseEntity<PetBoardDTO>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<PetBoardDTO>(HttpStatus.UNAUTHORIZED);
		}
	}

	// 자랑게시판 게시글 수정
		@HasRoleUser
		@PostMapping(value="/bestPetsUpdate",
					consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> updatePetBoard(@RequestBody PetBoardDTO dto){
			try {
				int updateCount = service.updatePetBoard(dto);
				if (updateCount == 1) {
					return new ResponseEntity<>("success", HttpStatus.OK);
				} else {
					// 업데이트가 실패하면 UNAUTHORIZED(401) 응답을 반환
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		}

	// 자랑게시판 게시글 삭제
	@DeleteMapping("/bestPetsDelete/{bno}")
	public ResponseEntity<String> deletePetBoard(@PathVariable("bno") long bno) {
		System.out.println("delete PetBoard(Controller) : " + bno);
		return (service.deleteReview(bno) == 1) ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	// 자랑게시판 게시글 좋아요
	// 1. { } 번 게시글에 좋아요를 누른 사람 목록에서 유저 검색
	// 2. 목록에서 찾지 못하면 -> 좋아요 안누른 상태 -> 좋아요 처리
	// 3. 목록에서 찾으면 -> 좋아요 누른 상태 -> 좋아요 취소 처리
	@PostMapping(value = "/bestPetsBoardLikes", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reviewBoardLikes(@RequestBody PetBoardHeartDTO dto) {
		long bno = dto.getBno(); // 게시글 번호
		// 1. {bno}번 게시글에 좋아요를 누른 사람 목록에서 유저가 있는지 찾는다
		List<Long> list = peopleWhoLikes(bno);
		long user_no = dto.getUser_no();
		System.out.println("---------- Controller --------");
		System.out.println(bno + " 번 게시글에 좋아요 한 사람들 목록 --");
		System.out.println(list.toString());
		System.out.println("현재 유저 번호 : " + user_no);
		System.out.println("-------------------------------");
		try {
			if (list.contains(user_no) == false) { // 유저가 좋아요를 누르지 않았으면
													// 좋아요를 1증가시키고, review_reply_heart 테이블에 정보 추가(좋아요번호(시퀀스), 댓글번호,
													// 유저번호)
				service.likesReply(dto);
			} else if (list.contains(user_no) == true) { // 유저가 이미 좋아요를 누른 상태면
															// 좋아요를 1감소 시키고, review_reply_heart 테이블에 정보 삭제(좋아요번호(시퀀스),
															// 댓글번호, 유저번호)
				service.likesReplyCancel(dto);
			}
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
	}

	// {postid} 번 댓글을 좋아요한 유저 번호 리스트 리턴하는 메소드
	public List<Long> peopleWhoLikes(long bno) {
		List<Long> list = new ArrayList<>();
		list = service.peopleWhoLikes(bno);
		return list;
	}

	// 자랑게시판 전체 페이지 수
	@GetMapping(value = "/bestPetsTotalPages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Long>> getTotalPage() {
		try {
			long end = service.getTotalPage(); // 마지막 페이지(전체페이지)
			Map<String, Long> response = new HashMap<>();
			response.put("end", end);
			return new ResponseEntity<Map<String, Long>>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Long>>(HttpStatus.UNAUTHORIZED);
		}
	}

	// 자랑게시판 통합 검색
	@GetMapping(value = "/bestPetsBoardSearch", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PetBoardDTO>> getSearchResult(@RequestParam("content") String content) {
		System.out.println("=== DB 통합 검색 컨트롤러 ====");
		try {
			List<PetBoardDTO> list = service.searchPetBoards(content);
			return new ResponseEntity<List<PetBoardDTO>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<PetBoardDTO>>(HttpStatus.UNAUTHORIZED);

		}
	}

	// 특정 게시글 좋아요한 유저 정보 리스트
	@GetMapping(value = "/bestPetsLikedList/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<UserDTO>> getPeopleWhoLikes(@PathVariable("bno") long bno) {
		try {
			List<UserDTO> list = service.getPeopleWhoLikes(bno);
			return new ResponseEntity<List<UserDTO>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<UserDTO>>(HttpStatus.GONE);
		}
	}

	@GetMapping("/bestPets")
	public ResponseEntity<Map<String, Object>> getBestPets() {
		Map<String, Object> response = new HashMap<>();
		try {
			response.put("status", "200");
			response.put("description", "대표게시물 통신 성공");
			response.put("data", service.getBestPets());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("status", "500");
			response.put("description", "Internal Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
}
