package net.developia.mini1st.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.developia.mini1st.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.Criteria;
import net.developia.mini1st.domain.ProductsDTO;
import net.developia.mini1st.domain.ReviewBoardHeartDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.ReviewDetailDTO;
import net.developia.mini1st.domain.UserDTO;
import net.developia.mini1st.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewMapper mapper;

	@Autowired
	public ReviewServiceImpl(ReviewMapper reviewMapper) {
		this.mapper = reviewMapper;
	}



	@Override
	public List<ReviewDTO> getReviewList(Criteria cri) {
		return mapper.getReviewList(cri);
	}

	@Override
	public void register(ReviewDTO dto) {
		// 배열로 들어온 img 를 하나의 String 으로 합치고
		// 다시 원소가 1개인 List 로 바꾸어 세팅
		// 마이바티스에서는 0번째 원소만 추출
		List<String> imgList = dto.getImg();
		String imgConcat = String.join(",", imgList);
		List<String> newImg = new ArrayList<>();
		newImg.add(imgConcat);
		dto.setImg(newImg);
		mapper.createReview(dto);
	}

	@Override
	public ReviewDTO readReview(long postid) {
		// DB 의 img 는 하나의 String 으로 저장
		// 이를 List<String> 으로 바꿔서 dto에 저장해야 한다
		ReviewDTO dto = mapper.readReview(postid);
		String dbImg = mapper.getImgString(postid);
		dto.setImg(Arrays.asList(dbImg.split(",")));
		return dto;
	}

	public ReviewDetailDTO getDetail(long postid) {
		// review_board 와 user_info 테이블에서
		// 필요한 데이터들만을 추출해 클라이언트에게 반환
		ReviewDTO reviewDTO = readReview(postid);
		// postid 번 게시글을 작성한 사람의 정보
		UserDTO userDTO = mapper.getUserInfo(postid);
		// 사용자에게 리턴할 ReviewDetailDTO 생성
		ReviewDetailDTO reviewDetailDTO = new ReviewDetailDTO();
		reviewDetailDTO.setPostid(reviewDTO.getPostid());
		reviewDetailDTO.setTitle(reviewDTO.getTitle());
		reviewDetailDTO.setContent(reviewDTO.getContent());
		reviewDetailDTO.setViews(reviewDTO.getView());
		reviewDetailDTO.setLikes(reviewDTO.getLikes());
		reviewDetailDTO.setImg(reviewDTO.getImg());
		reviewDetailDTO.setStars(reviewDTO.getStars());
		reviewDetailDTO.setItemid(reviewDTO.getItemid());
		reviewDetailDTO.setNickname(userDTO.getNickname());
		reviewDetailDTO.setImg_url(userDTO.getImg_url());
		return reviewDetailDTO;
	}

	@Override
	public void updateReview(ReviewDTO dto) {
		List<String> imgList = dto.getImg();
		String imgConcat = String.join(",", imgList);
		List<String> newImg = new ArrayList<>();
		newImg.add(imgConcat);
		dto.setImg(newImg);
		System.out.println("dto = " + dto);
		mapper.updateReview(dto);
	}

	@Override
	public int deleteReview(long postid) {
		mapper.deleteReplyCascade(postid);
		return mapper.deleteReview(postid);
	}

	@Override
	public long getTotalCount(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<Long> peopleWhoLikes(long postid) {
		return mapper.peopleWhoLikes(postid);
	}

	@Override
	public void likesReply(ReviewBoardHeartDTO dto) {
		// 유저가 좋아요를 누르지 않은 상태
		// 좋아요를 1증가시키고, review_board_heart 테이블에 정보 추가(좋아요번호(시퀀스), 댓글번호, 유저번호)
		long user_no = dto.getUser_no(); // 유저 번호
		long postid = dto.getPostid(); // 게시글 번호
		System.out.println("======= service ==========");
		System.out.println("user_no = " + user_no);
		System.out.println("postid = " + postid);
		System.out.println("==========================");
		// 1. 좋아요 1 증가
		mapper.increaseLikes(postid);
		// 2. review_reply_heart 테이블에 추가
		mapper.likesReply(dto);
	}

	@Override
	public void likesReplyCancel(ReviewBoardHeartDTO dto) {
		// 유저가 좋아요를 이미 누른 상태
		// 좋아요를 1감소시키고, review_board_heart 테이블에서 정보 삭제(좋아요번호(시퀀스), 댓글번호, 유저번호)
		long postid = dto.getPostid(); // 댓글 번호
		long hno = dto.getHno(); // 좋아요 번호
		System.out.println("*********** service(좋아요취소) *********");
		System.out.println("postid = " + postid);
		System.out.println("hno = " + hno);
		System.out.println("*************************************");
		// 1. 좋아요 1 감소
		mapper.decreaseLikes(postid);
		// 2. review_reply_heart 테이블에 추가
		mapper.likesReplyCancel(dto);
	}

	// 제품 상세 정보 조회
	@Override
	public ProductsDTO getProductDetail(long product_id) {
		return mapper.getProductDetail(product_id);
	}

	@Override
	public List<ReviewDTO> searchReviews(String keyword) {
		return mapper.searchReviews(keyword);
	}

	@Override
	public void increaseViews(long postid) {
		mapper.increaseViews(postid);
	}
	
	@Override
	public List<ReviewDTO> getBestReview() {
		return mapper.getBestReview();
	}

	@Override
	public long getTotalPage() {
		Criteria cri = new Criteria();
		long totalRecord = mapper.getTotalCount(cri);
		long onePageRecord = (long)cri.getAmount();
		if (totalRecord <= onePageRecord) {
			return 1;
		}
		long totalPages = (totalRecord % onePageRecord == 0) ? 
				(totalRecord / onePageRecord) 
				: (totalRecord / onePageRecord + 1);
		return totalPages;
	}

	@Override
	public List<ReviewDTO> getReviewsByItem(String product_name) {
		return mapper.getReviewsByItem(product_name);
	}

	// postid 번 게시글 좋아요 누른 사람 리스트 List<UserDTO>
	@Override
	public List<UserDTO> getPeopleWhoLikes(long postid) {
		return mapper.getPeopleWhoLikes(postid);
	}

}
