package dao;

import com.chzu.dao.TbGradeMapper;
import com.chzu.dao.TbStudentMapper;
import com.chzu.model.bo.GradeInfo;
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
public class TestGradeDao {
    @Resource
    private TbGradeMapper gradeMapper;

    @Test
    public void getByTeaId() {
        List<GradeInfo> gradeList = gradeMapper.getByTeaId("030805060687791999");
        for (GradeInfo grade : gradeList) {
            System.out.println(grade);
        }
    }

    @Test
    public void getByClaName() {
        List<GradeInfo> gradeList = gradeMapper.getByClaName("计科17");
        for (GradeInfo grade : gradeList) {
            System.out.println(grade);
        }
    }
}
