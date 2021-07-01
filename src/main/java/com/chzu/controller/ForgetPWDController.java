package com.chzu.controller;

import com.chzu.service.StudentService;
import com.chzu.service.TeacherService;
import com.chzu.util.Constant;
import com.chzu.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "忘记密码的相关操作",tags = "忘记密码的相关操作")
@Controller
@RequestMapping("forgetPWD")
public class ForgetPWDController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "发送验证码",notes = "发送验证码",httpMethod = "POST")
    @PostMapping("sendEmail")
    @ResponseBody
    public JSONResult sendEmail(
            @ApiParam(name = "account",value = "账号",required = true,example = "2017211685")
            String account) {
        //1. 确认账号类型，是教师还是学生
        //2. 查询用户邮箱，发送验证码
        account = account.replace("\"","");
        if(studentService.accountIsExisted(account)) {
            studentService.sendEmail(account);
            return JSONResult.ok();
        }else if(teacherService.accountIsExisted(account)) {
            teacherService.sendEmail(account);
            return JSONResult.ok();
        }else {
            return JSONResult.errorMsg("该账号不存在！！！");
        }

    }

    @ApiOperation(value = "忘记密码",notes = "忘记密码接口",httpMethod = "POST")
    @PostMapping("verify")
    @ResponseBody
    public JSONResult verify(
            @ApiParam(name = "account",value = "账号",required = true,example = "2017211685")
            String account ,
            @ApiParam(name = "code",value = "验证码",required = true,example = "654825")
            String code) {
        if(!Constant.codeMap.containsKey(account)) {
            return JSONResult.errorMsg("账号输入有误！");
        }
        if(Constant.codeMap.get(account).equals(code)) {
            return JSONResult.ok();
        }else {
            return JSONResult.errorMsg("验证码错误！");
        }
    }

    @ApiOperation(value = "重置密码",notes = "重置密码",httpMethod = "POST")
    @PostMapping("resetPassword")
    @ResponseBody
    public JSONResult resetPassword(
            @ApiParam(name = "account",value = "账号",required = true,example = "2017211685")
            String account ,
            @ApiParam(name = "password",value = "密码",required = true,example = "654825")
            String password) {
        if(studentService.accountIsExisted(account)) {
            studentService.resetPassword(account,password);
            return JSONResult.ok();
        }else {
            teacherService.resetPassword(account,password);
            return JSONResult.ok();
        }
    }
}
