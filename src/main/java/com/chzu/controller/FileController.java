package com.chzu.controller;

import com.chzu.model.TbFile;
import com.chzu.model.bo.ReportFileInfo;
import com.chzu.model.vo.TransferData;
import com.chzu.service.ClassService;
import com.chzu.service.FileService;
import com.chzu.service.StudentService;
import com.chzu.service.TeacherService;
import com.chzu.util.JSONResult;
import com.chzu.util.TableDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Api(value = "文件上传等操作",tags = "文件上传等操作")
@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ClassService classService;

    @ApiOperation(value = "批量上传学生信息",notes = "批量上传学生信息",httpMethod = "POST")
    @PostMapping("uploadStudentExcel")
    @ResponseBody
    public JSONResult uploadStudentExcel(@RequestParam("file") MultipartFile file,
                                       @ApiParam(name = "upId",value = "上传者id",required = true,example = "0123030309347781")
                                               String upId,
                                       HttpServletRequest request,
                                       HttpServletResponse response)throws Exception{
        studentService.uploadExcelStudent(file,upId,request,response);
        return JSONResult.ok();
    }

    @ApiOperation(value = "批量上传教师信息",notes = "批量上传教师信息",httpMethod = "POST")
    @PostMapping("uploadTeacherExcel")
    @ResponseBody
    public JSONResult uploadTeacherExcel(@RequestParam("file") MultipartFile file,
                                         @ApiParam(name = "upId",value = "上传者id",required = true,example = "0123030309347781")
                                         String upId,
                                         HttpServletRequest request) throws Exception{
        teacherService.uploadTeacherExcel(file,upId,request);
        return JSONResult.ok();
    }

    @ApiOperation(value = "上传大纲或者课程审查表",notes = "上传大纲或者课程审查表",httpMethod = "POST")
    @PostMapping("uploadData")
    @ResponseBody
    public JSONResult uploadData(@RequestParam("file") MultipartFile file,
                                 @ApiParam(name = "upId",value = "上传者id",required = true,example = "0123030309347781")
                                 String upId,
                                 @ApiParam(name = "fileType",value = "文件类型",required = true,example = "0123030309347781")
                                 String fileType,
                                 HttpServletRequest request) throws Exception{
        if(file.isEmpty()){
            return JSONResult.errorMsg("文件不存在！");
        }
        teacherService.uploadData(file,upId,fileType,request);
        return JSONResult.ok();

    }

    @ApiOperation(value = "上传大纲或者课程审查表",notes = "上传大纲或者课程审查表",httpMethod = "POST")
    @PostMapping("uploadProgressReport")
    @ResponseBody
    public JSONResult uploadProgressReport(@RequestParam("file") String file,
                                           @ApiParam(name = "upId",value = "上传者id",required = true,example = "0123030309347781")
                                           String upId,
                                           HttpServletRequest request){
        uploadProgressReport(file,upId,request);
        return JSONResult.ok();
    }

    @ApiOperation(value = "根据id删除",notes = "根据id删除",httpMethod = "POST")
    @PostMapping("deleteById")
    @ResponseBody
    public JSONResult deleteById(
            @ApiParam(name = "id",value = "文件id",required = true,example = "0123030309347781")
            String id){
        fileService.deleteById(id);
        return JSONResult.ok();
    }

    @ApiOperation(value = "文件下载",notes = "文件下载",httpMethod = "GET")
    @GetMapping("download")
    @ResponseBody
    public void download(
            HttpServletRequest request,
            HttpServletResponse response,
            @ApiParam(name = "fileId",value = "文件id",required = true,example = "0123030309347781")
            String fileId) throws IOException {
        //查找数据库文件的相关信息
        TbFile fileInfo = fileService.getById(fileId);

        //得到要下载的文件
        File file = new File(fileInfo.getFilePath());
        if (!file.exists()) {
            response.setContentType("text/html; charset=UTF-8");//注意text/html，和application/html
            response.getWriter().print("<html><body><script type='text/javascript'>alert('您要下载的资源已被删除！');</script></body></html>");
            response.getWriter().close();
            System.out.println("您要下载的资源已被删除！！");
            return;
        }
        //转码，免得文件名中文乱码
        String filename = URLEncoder.encode(fileInfo.getFileName(),"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(fileInfo.getFilePath());
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 创建缓冲区
        byte buffer[] = new byte[1024]; // 缓冲区的大小设置是个迷  我也没搞明白
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while((len = in.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        // 关闭输出流
        out.close();

    }

    @ApiOperation(value = "添加汇报文件",notes = "添加汇报文件",httpMethod = "POST")
    @PostMapping("addProcessReport")
    @ResponseBody
    public JSONResult addProcessReport(
            @ApiParam(name = "title",value = "报告标题",required = true,example = "xxxxx-20201205-进度汇报")
            String title,
            @ApiParam(name = "content",value = "报告内容",required = true,example = "XXXXXXXXX")
            String content,
            @ApiParam(name = "upId",value = "上传者id",required = true,example = "0123030309347781")
            String upId) {
       if(title == null || title.equals(""))
           return JSONResult.errorMsg("标题为空！");
       String[] split = title.split("-");
       if (!split[0].matches("[0-9]+") || !split[1].matches("[0-9]+"))
            return JSONResult.errorMsg("标题格式错误！");
       if (split[0].length() != 10)
           return JSONResult.errorMsg("学号格式错误！");
       if (split[1].length() != 8)
           return JSONResult.errorMsg("日期格式错误！");
       if(content == null || content.equals("")) return JSONResult.errorMsg("内容为空！");
       fileService.addProcessReport(title,content,upId);
       return JSONResult.ok();
    }

    @ApiOperation(value = "查询汇报文件的信息",notes = "查询汇报文件的信息",httpMethod = "GET")
    @GetMapping("getReport")
    @ResponseBody
    public TableDate getReport(
            @ApiParam(name = "teaId",value = "教师id",required = true,example = "0123030309347781")
            String teaId) {
        List<TransferData> classList = classService.getClassInfoByTeaId(teaId);
        List<ReportFileInfo> reportList = fileService.getReport(classList);
        if (reportList.size() > 0)
            return TableDate.ok(reportList,reportList.size());
        else
            return TableDate.error("无相关数据");
    }

    @ApiOperation(value = "阅读汇报的内容信息",notes = "阅读汇报的内容信息",httpMethod = "POST")
    @PostMapping("readReport")
    @ResponseBody
    public JSONResult readReport(
            @ApiParam(name = "id",value = "文件id",required = true,example = "0123030309347781")
            String id) {
        if (id == null || id.equals("")) return JSONResult.errorMsg("id为空！");
        String reportContent = fileService.getReportContent(id);
        return JSONResult.ok(reportContent);
    }

    @ApiOperation(value = "按条件查询汇报文件的信息",notes = "按条件查询汇报文件的信息",httpMethod = "GET")
    @GetMapping("queryReportByClaNameOrNumber")
    @ResponseBody
    public TableDate queryReportByClaNameOrNumber(String className,String number) {
        List<ReportFileInfo> reportList = fileService.queryReportByClaNameOrNumber(className, number);
        if (reportList.size() > 0)
            return TableDate.ok(reportList,reportList.size());
        else return TableDate.error("无相关数据");
    }

}
