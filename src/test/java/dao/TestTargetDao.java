package dao;

import com.chzu.dao.TbTargetMapper;
import com.chzu.model.TbTarget;
import com.chzu.model.bo.GradeInfo;
import com.chzu.util.TargetCalculationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TestTargetDao {
    @Resource
    private TbTargetMapper targetMapper;

    @Test
    public void test(){
        List<TbTarget> tbTargets = targetMapper.selectAll();
        TargetCalculationUtil.getRawData(tbTargets);

        List<GradeInfo> gradeInfoList = new ArrayList<>();

        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setGrade1(78);
        gradeInfo.setGrade2(78);
        gradeInfo.setGrade3(78);
        gradeInfo.setGrade4(78);
        gradeInfo.setGrade5(78);
        gradeInfoList.add(gradeInfo);

        GradeInfo g2 = new GradeInfo();
        g2.setGrade1(88);
        g2.setGrade2(88);
        g2.setGrade3(88);
        g2.setGrade4(88);
        g2.setGrade5(88);
        gradeInfoList.add(g2);

        TargetCalculationUtil.calculateGrade(gradeInfoList);

    }

    @Test
    public void t() {
        double dou = 3.1;
        BigDecimal d2 = new BigDecimal(dou).setScale(2, RoundingMode.HALF_UP);
        BigDecimal d1 = new BigDecimal(3.56).setScale(2,RoundingMode.HALF_UP);
        d2 = d2.add(d1);
        System.out.println(d2);
    }
}
