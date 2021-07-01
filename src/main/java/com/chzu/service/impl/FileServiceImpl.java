package com.chzu.service.impl;

import com.chzu.dao.FileMapper;
import com.chzu.dao.TbFileMapper;
import com.chzu.enums.FileType;
import com.chzu.model.TbFile;
import com.chzu.model.TbStudent;
import com.chzu.model.bo.ReportFileInfo;
import com.chzu.model.vo.TransferData;
import com.chzu.service.FileService;
import com.chzu.util.SmallUtil;
import com.chzu.util.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("fileService")
public class FileServiceImpl implements FileService {
    @Autowired
    private TbFileMapper fileMapper;
    @Autowired
    private FileMapper fileDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(String id) {
        TbFile file = new TbFile();
        file.setId(id);
        fileMapper.delete(file);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public TbFile getById(String id) {
        Example example = new Example(TbFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);
        return fileMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addProcessReport(String title, String content,String upId) {
        String path = "E:\\design-manager-system\\uploads\\" + FileType.progressReport.value + "\\" + title + ".txt";
        File report = new File(path);
        //新建文件及文件夹
        try {
            File parent = new File(report.getParent());
            if (!parent.exists()) parent.mkdirs();
            if (!report.exists())report.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedOutputStream out = null;
        try {
            try {
                out = new BufferedOutputStream(new FileOutputStream(report));
                out.write(content.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) out.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        TbFile file = new TbFile();
        file.setId(SmallUtil.getGeneratID());
        file.setFileType(FileType.progressReport.type);
        file.setFilePath(path);
        file.setFileName(title);
        file.setUpTime(new Date());
        file.setUpId(upId);
        fileMapper.insert(file);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ReportFileInfo> getReport(List<TransferData> classList) {
        List<ReportFileInfo> reportList = new ArrayList<>();
        for (TransferData data : classList) {
            if (data.getIsRight().equals("true")) {
                reportList.addAll(fileDao.getReportInfo(data.getValue()));
            }
        }
        return reportList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getReportContent(String id) {
        TbFile file = fileMapper.selectByPrimaryKey(id);
        String content = "";
        InputStreamReader reader = null;
        try{
            reader = new InputStreamReader(new FileInputStream(new File(file.getFilePath())));
            char[] chars = new char[1024];
            int b = 0;
            while ((b = reader.read(chars,0,chars.length)) != -1) {
                content += new String(chars);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ReportFileInfo> queryReportByClaNameOrNumber(String claName, String number) {
        return fileDao.getReportInfoByClaNameOrNumber(claName,number);
    }
}
