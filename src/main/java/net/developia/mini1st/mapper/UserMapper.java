package net.developia.mini1st.mapper;


import net.developia.mini1st.domain.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (email, password, nickname) VALUES (#{email}, #{password}, #{nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "user_id")
    void insertUser(UserDTO userDTO);

    @Select("SELECT COUNT(*) FROM users WHERE nickname = #{nickname}")
    int countUsersByNickname(String nickname);


}
