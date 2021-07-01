package Service;

import com.chzu.model.vo.TransferData;
import com.chzu.service.ClassService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TestClassService {
    @Resource
    private ClassService classService;

    @Test
    public void getClassInfoByTeaId() {
        List<TransferData> list = classService.getClassInfoByTeaId("030805060657111077");
        for (TransferData d : list) {
            System.out.println(d.getTitle() + " ------ "  + d.getIsRight());
        }
    }
}
