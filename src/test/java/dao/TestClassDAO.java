package dao;

import com.chzu.dao.TbClassMapper;
import com.chzu.dao.TbStudentMapper;
import com.chzu.enums.Sex;
import com.chzu.model.TbClass;
import com.chzu.model.TbStudent;
import com.chzu.util.ReadExcelUtil;
import com.chzu.util.SmallUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TestClassDAO {

    @Autowired
    private TbClassMapper classMapper;
    @Autowired
    private TbStudentMapper studentMapper;


    @Test
    public void getIdByClassName(){



    }

    @Test
    public void getByClaName(){

        Example example = new Example(TbClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("claName","计科17");
        TbClass cla = classMapper.selectOneByExample(example);
        if( cla == null)
            System.out.println("查不到计科17");

    }


    @Test
    public void updateNumById(){

        classMapper.updateNumById("0307070103529296",10);
    }

    @Test
    public void getNotBuildRelationship() throws IOException {
        List<TbClass> list = classMapper.getBuildedRelationship("030805060657111077");
        for (TbClass cla : list) {
            System.out.println(cla.getClaName());
        }

    }

    @Test
    public void getClassesByTeacherId(){

    }
}
