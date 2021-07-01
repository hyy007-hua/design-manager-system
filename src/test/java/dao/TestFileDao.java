package dao;

import com.chzu.dao.FileMapper;
import com.chzu.dao.TbFileMapper;
import com.chzu.enums.FileType;
import com.chzu.model.TbFile;
import com.chzu.model.bo.ReportFileInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TestFileDao {

    @Autowired
    private TbFileMapper fileMapper;
    @Resource
    private FileMapper fileDao;

    @Test
    public void getByUpIdAndFileType(){
        List<TbFile> fileList = fileMapper.getByUpIdAndFileType("030805060687791999", FileType.outline.type);
        for(TbFile file : fileList){
            System.out.println(file.getFileName());
        }
    }

    @Test
    public void getReportInfo() {
        List<ReportFileInfo> reportList = fileDao.getReportInfo("0307070103529296");
        if (reportList != null) {
            for (ReportFileInfo report : reportList) {
                System.out.println(report.getFileName() + "----" + report.getName());
            }
        }

    }

}
