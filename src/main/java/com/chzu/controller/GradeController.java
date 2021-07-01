package com.chzu.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chzu.model.TbFile;
import com.chzu.model.bo.GradeInfo;
import com.chzu.service.GradeService;
import com.chzu.util.JSONResult;
import com.chzu.util.TableDate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Api(value = "成绩",tags = "与学生成绩相关的接口")
@Controller
@RequestMapping("grade")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @ApiOperation(value = "查询学生成绩列表",notes = "查询学生成绩列表",httpMethod = "GET")
    @GetMapping("getGradeListByTeaId")
    @ResponseBody
    public TableDate getGradeListByTeaId(
            @ApiParam(name = "teaId",value = "教师id",required = true,example = "1212044344639040")
            String teaId) {
        List<GradeInfo> gradeList = gradeService.getGradeListByTeaId(teaId);
        if (gradeList == null || gradeList.size() == 0) {
            return TableDate.error("无相关数据");
        }else {
            return TableDate.ok(gradeList,gradeList.size());
        }
    }

    @ApiOperation(value = "根据班级名查询学生成绩列表",notes = "根据班级名查询学生成绩列表",httpMethod = "GET")
    @GetMapping("queryGradeListByClaName")
    @ResponseBody
    public TableDate queryGradeListByClaName(
            @ApiParam(name = "claName",value = "班级名称",required = true,example = "计科17")
            String className) {
        List<GradeInfo> gradeList = gradeService.getGradeListByClaName(className);
        if (gradeList == null || gradeList.size() == 0) {
            return TableDate.error("无相关数据");
        }else {
            return TableDate.ok(gradeList,gradeList.size());
        }
    }

    @ApiOperation(value = "下载目标达成度计算表格",notes = "下载目标达成度计算表格",httpMethod = "POST")
    @PostMapping("generateAchievementTable")
    @ResponseBody
    public JSONResult generateAchievementTable(
            HttpServletRequest request,
            HttpServletResponse response,
            @ApiParam(name = "data",value = "学生成绩列表JSON数据",required = true,example = "")
            @RequestBody
            String data)  throws IOException  {
        //将json数组数据转换为list对象
        JSONArray dataArray = JSONObject.parseArray(data);
        List<GradeInfo> gradeInfoList = dataArray.toJavaList(GradeInfo.class);

        //将学生成绩传给业务层，生成达成度表格
        TbFile file = gradeService.generateAchievementTable(gradeInfoList);

        return JSONResult.ok(file.getId());
    }
}
