package net.developia.mini1st.mapper;

import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface MyPageMapper {
	public UserDTO getUserInfo(int user_no);

	@Select("SELECT * FROM pet_board WHERE bno = #{bno}")
	public PetBoardDTO getBestPet(long bno);

	@Select("SELECT * FROM review_board WHERE postid = #{postid}")
	public ReviewDTO getReview(long postid);
}
