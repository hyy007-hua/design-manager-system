package Service;

import com.chzu.model.vo.TreeData;
import com.chzu.service.TeacherService;
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
public class TestTeacherService {
    @Resource
    private TeacherService teacherService;

    @Test
    public void linkTeacherAndClass(){

    }
    @Test
    public void delLinkTeacherAndClass(){

    }
    @Test
    public void queryDataByTeaId(){
        List<TreeData> list = teacherService.queryDataByTeaId("030805060687791999");
        for(TreeData data : list){
            System.out.println("list.size:"+data.getChildren().size());
            if(data.getChildren().size() > 0)
                System.out.println("file:"+data.getChildren().get(0).getName());
        }
    }
}
