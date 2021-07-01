package Service;

import com.chzu.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TestGroupService {
    @Resource
    private GroupService groupService;

    @Test
    public void isGrouped() {
        boolean grouped = groupService.isGrouped("0307070103529296");
        System.out.println(grouped);
    }
}
