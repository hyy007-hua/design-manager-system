package Service;

import com.chzu.model.TbFile;
import com.chzu.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TestFileService {
    @Resource
    private FileService fileService;

    @Test
    public void getById(){
        TbFile file = fileService.getById("031505020553363079");
        System.out.println(file.getFileName());
    }
}
