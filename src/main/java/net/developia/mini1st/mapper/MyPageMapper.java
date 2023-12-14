package net.developia.mini1st.mapper;

import net.developia.mini1st.domain.PetBoardDTO;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.domain.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface MyPageMapper {

	public UserDTO getUserInfo(int user_no);

	@Select("SELECT * FROM pet_board WHERE user_no = #{user_no}")
	public PetBoardDTO getBestPet(long bno);

	@Select("SELECT * FROM review_board WHERE user_no = #{user_no}")
	public ReviewDTO getReview(long postid);

	public UserDTO getUserInfo(String email);

}
