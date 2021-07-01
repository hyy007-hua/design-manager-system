package dao;

import com.chzu.dao.TbStudentMapper;
import com.chzu.model.TbStudent;
import com.chzu.util.M5Util;
import com.chzu.util.SmallUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TestStudentDao {

    @Autowired
    private TbStudentMapper studentMapper;

    @Test
    public void getAll(){

    }
    @Test
    public void save() throws Exception {

    }
    @Test
    public void getByNumber(){

   }
    @Test
    public void getByTimeAndNumber() throws ParseException {

   }
   @Test
    public void delById(){

   }

   @Test
    public void countNumberByClaName(){

   }

   @Test
   public void getByClaNameOrNumber(){
        List<TbStudent> list = studentMapper.getByClaNameOrNumber("计科17","2017");
        for (TbStudent student : list){
            System.out.println(student.getName());
        }
   }

   @Test
    public void getByTeacherId(){

   }

   @Test
    public void countByCondition(){
       TbStudent student = new TbStudent();
       student.setClassId("0307070103529296");
       int count = studentMapper.selectCount(student);
       System.out.println(count);
   }

}
