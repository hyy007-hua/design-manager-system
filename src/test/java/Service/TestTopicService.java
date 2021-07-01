package Service;

import com.chzu.model.TbTopic;
import com.chzu.service.TopicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TestTopicService {
    @Resource
    private TopicService topicService;

    @Test
    public void queryByStuId() {
        TbTopic topic = topicService.queryByStuId("031305093024357926");
        if (topic == null) System.out.println("this is null");

    }
}
