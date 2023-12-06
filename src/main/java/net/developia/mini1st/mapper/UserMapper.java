package net.developia.mini1st.mapper;


import net.developia.mini1st.domain.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (email, password, nickname) VALUES (#{email}, #{password}, #{nickname})")
    @Options(useGeneratedKeys = true, keyProperty = "user_no", keyColumn = "user_no")
    void insertUser(UserDTO userDTO);


}
