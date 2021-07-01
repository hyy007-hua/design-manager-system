package com.chzu.controller;

import com.chzu.model.TbGrade;
import com.chzu.model.TbTeacher;
import com.chzu.model.vo.TargetVO;
import com.chzu.model.vo.TeacherVO;
import com.chzu.model.vo.TreeData;
import com.chzu.service.GradeService;
import com.chzu.service.TargetService;
import com.chzu.service.TeacherService;
import com.chzu.util.JSONResult;
import com.chzu.util.TableDate;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.util.List;

/**
 * @author hyy
 */
@Api(value = "教师",tags = "与教师相关的接口")
@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TargetService targetService;
    @Autowired
    private GradeService gradeService;

    @ApiOperation(value = "保存教师信息",notes = "保存教师信息",httpMethod = "POST")
    @PostMapping("save")
    @ResponseBody
    public JSONResult save(TbTeacher teacher) {
        int result = teacherService.save(teacher);
        if(result == 1)
            return JSONResult.ok();
        else
            return JSONResult.errorMsg("操作失败！");
    }



    @ApiOperation(value = "查询所有教师信息",notes = "查询所有教师信息",httpMethod = "GET")
    @GetMapping("getAll")
    @ResponseBody
    public TableDate getAll(
            @ApiParam(name = "page",value = "页码",required = true,example = "1") int page,
            @ApiParam(name = "limit",value = "页面数",required = true,example = "10,20") int limit){
        PageInfo<TeacherVO> teacherList = teacherService.getAll(page,limit);
        return TableDate.ok(teacherList.getList(),teacherList.getSize());
    }

    @ApiOperation(value = "建立班级与教师的关系",notes = "建立班级与教师的关系",httpMethod = "POST")
    @PostMapping("linkTeacherAndClass")
    @ResponseBody
    public JSONResult linkTeacherAndClass(
            @ApiParam(name = "teaId",value = "教师id",required = true,example = "1212044344639040")
            @RequestParam String teaId,
            @ApiParam(name = "classIds",value = "班级id组字符串",required = true,example = "'1212121212,1212121212,'")
            @RequestParam String classIds){
        String[] ids = classIds.split(",");
        int result = teacherService.linkTeacherAndClass(teaId,ids);
        if(result == ids.length){
            return JSONResult.ok();
        }else{
            return JSONResult.errorMsg("操作失败");
        }
    }

    @ApiOperation(value = "删除班级与教师的关系",notes = "删除班级与教师的关系",httpMethod = "POST")
    @PostMapping("delLinkTeacherAndClass")
    @ResponseBody
    public JSONResult delLinkTeacherAndClass(
            @ApiParam(name = "teaId",value = "教师id",required = true,example = "1212044344639040")
            @RequestParam String teaId,
            @ApiParam(name = "classIds",value = "班级id组字符串",required = true,example = "'1212121212,1212121212,'")
            @RequestParam String classIds){
        String[] ids = classIds.split(",");
        int result = teacherService.delLinkTeacherAndClass(teaId,ids);
        if(result > 0){
            return JSONResult.ok();
        }else{
            return JSONResult.errorMsg("操作失败");
        }
    }

    @ApiOperation(value = "重新设置教师密码",notes = "重新设置教师密码",httpMethod = "POST")
    @PostMapping("resetPassword")
    @ResponseBody
    public JSONResult resetPassword(
            @ApiParam(name = "id",value = "教师id",required = true,example = "1212044344639040")
            String id,
            @ApiParam(name = "number",value = "教师编号",required = true,example = "2020005")
            String number,
            @ApiParam(name = "oldPass",value = "旧密码",required = true,example = "123456")
            String oldPass,
            @ApiParam(name = "newPass",value = "新密码",required = true,example = "1234567")
            String newPass,
            @ApiParam(name = "rePass",value = "确认新密码",required = true,example = "123456")
            String rePass) {
        //1.确认输入新密码两次是否一致
        if(!newPass.equals(rePass)){
            return JSONResult.errorMsg("两次输入的密码不一致！");
        }
        //2.确认旧密码是否正确
        if(teacherService.confirmOldPassword(id,oldPass) == false){
            return JSONResult.errorMsg("旧密码输入错误！");
        }
        //3.更改密码
        if(teacherService.resetPassword(number,newPass)){
            return JSONResult.ok();
        }else{
            return JSONResult.errorMsg("操作失败！");
        }
    }


    @ApiOperation(value = "更新教师个人信息",notes = "更新教师个人信息",httpMethod = "POST")
    @PostMapping("updateInfo")
    @ResponseBody
    public JSONResult updateInfo(
            @ApiParam(name = "number",value = "教师编号",required = true,example = "2020001")
            String number,
            @ApiParam(name = "sex",value = "教师性别",required = true,example = "1")
            int sex,
            @ApiParam(name = "email",value = "教师邮箱",required = true,example = "19546235486@qq.com")
            String email,
            @ApiParam(name = "telNumber",value = "教师联系电话",required = true,example = "17885622586")
            String telNumber){
        int result = teacherService.updateInfo(number,sex,email,telNumber);
        if(result > 0){
            return JSONResult.ok();
        }else{
            return JSONResult.errorMsg("操作失败");
        }
    }

    @ApiOperation(value = "查询大纲及课程审查表相关信息",notes = "查询大纲及课程审查表相关信息",httpMethod = "POST")
    @PostMapping("queryDataByTeaId")
    @ResponseBody
    public JSONResult queryDataByTeaId(
            @ApiParam(name = "teaId",value = "教师id",required = true,example = "1212044344639040")
            String teaId){
        List<TreeData> treeDataList = teacherService.queryDataByTeaId(teaId);
        return JSONResult.ok(treeDataList);
    }

    @ApiOperation(value = "添加课程目标",notes = "添加课程目标",httpMethod = "POST")
    @PostMapping("addObjective")
    @ResponseBody
    public JSONResult addObjective(TargetVO target,
                                   String upId) {
        targetService.addObjective(target,upId);
        return JSONResult.ok();
    }

    @ApiOperation(value = "给学生打分",notes = "给学生打分",httpMethod = "POST")
    @PostMapping("gradeForStudent")
    @ResponseBody
    public JSONResult gradeForStudent(TbGrade grade) {
        //是否已经评分
        if (gradeService.isGrade(grade.getStudentId())) return JSONResult.errorMsg("您已经对这位同学评分了！");
        //分数校验
        if (grade.getGrade1() < 0 || grade.getGrade1() >100 || grade.getGrade2() < 0 || grade.getGrade2() >100 ||grade.getGrade3() < 0 || grade.getGrade3() >100 ||grade.getGrade4() < 0 || grade.getGrade4() >100 ||grade.getGrade5() < 0 || grade.getGrade5() >100 )
            return JSONResult.errorMsg("成绩输入错误，应该在0~100的范围内！");
        gradeService.gradeForStudent(grade);
        return JSONResult.ok();
    }

}
