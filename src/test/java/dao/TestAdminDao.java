package dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestAdminDao {

    @Test
    public void getByAccount(){
        //AdminEntity admin = adminDAO.getByAccount("2020");
        //System.out.println(admin.getPassword());
    }
}
