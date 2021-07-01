package com.chzu.service;

import com.chzu.model.TbFile;
import com.chzu.model.TbGrade;
import com.chzu.model.bo.GradeInfo;

import java.util.List;

public interface GradeService {

    boolean isGrade(String stuId);

    /**
     * 教师给学生打分
     * @param grade
     */
    void gradeForStudent(TbGrade grade);

    /**
     * 根据教师id查询其学生成绩列表
     * @param teaId
     * @return
     */
    List<GradeInfo> getGradeListByTeaId(String teaId);

    /**
     * 根据班级名称查询其学生成绩列表
     * @param claName
     * @return
     */
    List<GradeInfo> getGradeListByClaName(String claName);

    TbFile generateAchievementTable(List<GradeInfo> gradeInfoList);


}
