package net.developia.mini1st.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.developia.mini1st.domain.ReviewDTO;
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
		return mapper.createReview(dto);
	}
	

	@Override
	public ReviewDTO readReview(long postid) {
		return mapper.readReview(postid);
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
