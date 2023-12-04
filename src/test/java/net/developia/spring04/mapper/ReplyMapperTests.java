package net.developia.spring04.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import net.developia.mini1st.mapper.ReplyMapper;
import net.developia.spring04.domain.ReplyVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
    private Long[] bnoArr = {406L,407L,408L,409L,410L};

    @Setter(onMethod_ = @Autowired)
    private ReplyMapper mapper;

    @Test
    public void testCreate(){
        IntStream.rangeClosed(1,10).forEach(i ->{
            ReplyVO vo = new ReplyVO();
            System.out.println("1111");
            // 게시물의 번호
            vo.setBno(bnoArr[i%5]);
            vo.setReply("댓글 테스트 " + i);
            vo.setReplyer("replyer" + i);

            mapper.insert(vo);
        });
    }

    @Test
    public void testMapper() {
        log.info(mapper);
    }
}
