package net.developia.mini1st.controller;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;
import net.developia.mini1st.domain.ReviewDTO;
import net.developia.mini1st.service.ReviewService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml" , 
"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Slf4j
public class ReviewControllerTests {

	@Autowired
	private WebApplicationContext ctx;
	
	@Autowired
    private ReviewService reviewService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testGetReviewList() throws Exception {
                
	}
	
	@Test
	public void testCreateReview() {
		ReviewDTO dto = new ReviewDTO();
		dto.setTitle("");
		dto.setContent("");
		dto.setImg("");
	}
}
