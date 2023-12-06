package net.developia.mini1st.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;
import net.developia.mini1st.domain.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml" , 
"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log
public class MyPageControllerTests {

	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testGetUserInfo() throws Exception {
		int user_no = 101;
		
		MvcResult result = mockMvc.perform(get("/api/getUserInfo")
                .param("user_no", String.valueOf(user_no))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        // 응답에서 UserDTO 객체를 얻음
        String content = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        UserDTO userDTO = objectMapper.readValue(content, UserDTO.class);
        assertNotNull(userDTO);
        log.info("user -> " + userDTO.toString());
	}
}
