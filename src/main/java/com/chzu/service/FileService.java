package com.chzu.service;

import com.chzu.model.TbFile;
import com.chzu.model.bo.ReportFileInfo;
import com.chzu.model.vo.TransferData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface FileService {
    /**
     * 删除
     * @param id
     */
    public void deleteById(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public TbFile getById(String id);

    /**
     *
     * @param title
     * @param content
     */
    public void addProcessReport(String title,String content,String upId);

    /**
     * 查询汇报文件的信息
     * @param classList
     * @return
     */
    public List<ReportFileInfo> getReport(List<TransferData> classList);

    /**
     * 得到进度汇报的内容信息
     * @param id
     * @return
     */
    public String getReportContent(String id);

    /**
     * 按条件查询进度汇报信息
     * @param claName
     * @param number
     * @return
     */
    public List<ReportFileInfo> queryReportByClaNameOrNumber(String claName,String number);

}
