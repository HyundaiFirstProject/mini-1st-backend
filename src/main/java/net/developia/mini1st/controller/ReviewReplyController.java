package net.developia.mini1st.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.ReplyVO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.ReviewReplyDTO;
import net.developia.mini1st.service.ReviewReplyService;

@RestController
@RequestMapping("/api")
@Log
public class ReviewReplyController {

	@Autowired
	private ReviewReplyService service;
	
	// 후기 게시판 댓글 등록(Create)
	@PostMapping(value="/bestReviewsCommentsUpload", 
			consumes = "application/json")
	public ResponseEntity<ReviewReplyDTO> createReply(@RequestBody ReviewReplyDTO dto){
		log.info("create Reply : " + dto);
		System.out.println("========= Controller start ========");
		System.out.println("컨트롤러 createReply 호출....");
		System.out.println("ReplyDTO -> " + dto);
		System.out.println("========= Controller end ========");
		int createCount = service.createReply(dto);
		return (createCount == 1)? 
				new ResponseEntity<ReviewReplyDTO>(HttpStatus.OK)
				: new ResponseEntity<ReviewReplyDTO>(HttpStatus.UNAUTHORIZED);
	}
	
	// 후기 게시판 댓글 리스트 불러오기(READ)
	@GetMapping(value="/bestReviewsComments/{postid}",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReviewReplyDTO>> getReplyList(@PathVariable("postid") long postid){
		try {
			List<ReviewReplyDTO> list = service.getReplyList(postid);
			return new ResponseEntity<List<ReviewReplyDTO>>(list, HttpStatus.OK);
		}catch(Exception e) {
			System.out.println("Exception occured... => " + e.getMessage());
			return new ResponseEntity<List<ReviewReplyDTO>>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	// 후기 게시판 댓글 수정(UPDATE)
	@PostMapping(value="/bestReviewsCommentsUpdate",
				consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateReply(@RequestBody ReviewReplyDTO dto){
		// 넘어오는 정보 : id(작성자 번호), rno(댓글 번호), postid(원글 번호), content(댓글 내용)
		System.out.println("updateReply 호출(Controller)....");
		long	rno = dto.getRno();
		ReviewReplyDTO newDTO = service.getReply(rno);
		String	reply = dto.getReply();
		newDTO.setReply(reply); // 댓글 내용 수정
		System.out.println("dto(Controller) -> " + dto.toString());
		return (service.updateReply(newDTO) == 1) ?
				new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
	
	// 댓글 번호(rno) 에 해당하는 댓글DTO 리턴하는 메소드
	public ReviewReplyDTO getReply(long rno) {
		return service.getReply(rno);
	}
}
