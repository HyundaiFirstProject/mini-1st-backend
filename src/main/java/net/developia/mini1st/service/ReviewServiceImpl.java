package net.developia.mini1st.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<ReviewDTO> getReviewList() {
		return mapper.getReviewList();
	}

	@Override
	public int register(ReviewDTO dto) {
		// 배열로 들어온 img 를 하나의 String 으로 합치고
		// 다시 원소가 1개인 List 로 바꾸어 세팅
		// 마이바티스에서는 0번째 원소만 추출
		List<String> imgList = dto.getImg();
		String imgConcat = String.join(",", imgList);
		List<String> newImg = new ArrayList<>();
		newImg.add(imgConcat);
		dto.setImg(newImg);
		return mapper.createReview(dto);
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
	public int updateReview(ReviewDTO dto) {
		return mapper.updateReview(dto);
	}

	@Override
	public int deleteReview(long postid) {
		mapper.deleteReplyCascade(postid);
		return mapper.deleteReview(postid);
	}

//	@Override
//	public long getTotalCount(PagingVO vo) {
//		return mapper.getTotalCount(vo);
//	}



}
