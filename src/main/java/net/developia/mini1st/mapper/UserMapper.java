package net.developia.mini1st.mapper;


import net.developia.mini1st.domain.UserDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user_info (user_no, email, password, nickname) VALUES (user_info_seq.NEXTVAL, #{userDTO.email}, #{userDTO.password}, #{userDTO.nickname})")
    @SelectKey(statement="SELECT user_info_seq.CURRVAL FROM DUAL", keyProperty="userDTO.user_no", before=false, resultType=int.class)
    void insertUser(@Param("userDTO") UserDTO userDTO);




    @Select("SELECT COUNT(*) FROM user_info WHERE nickname = #{nickname}")
    int countUsersByNickname(String nickname);



    @Select("SELECT password FROM user_info WHERE user_no=#{user_no}")
	String getUserPassword(int user_no);
    @Select("SELECT * FROM user_info WHERE user_no = #{userId}")
    UserDTO getUserById(int userId);

    @Update("UPDATE user_info SET img_url = #{img_url},nickname = #{nickname} WHERE user_no = #{user_no}")
    void updateUserProfile(UserDTO userDTO);

    @Select("SELECT img_url FROM user_info WHERE user_no=#{user_no}")
    String getUserProfileImageUrl(int user_no);


    //@Select("SELECT COUNT(*) FROM user_info WHERE email = #{email} and password = #{password}")
    int login(String email, String password);
    
    int countEmail(String email);
    
    // 비밀번호 변경
    @Update("Update user_info set password=#{password} where email=#{email}")
    int changePwd(@Param("password") String password, @Param("email") String email);
    
    // 탈퇴
//    @Delete("delete from user_info where email = #{email}")
    int withdrawl(@Param("email")String email);
}
