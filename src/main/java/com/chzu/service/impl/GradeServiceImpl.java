package com.chzu.service.impl;

import com.chzu.dao.TbFileMapper;
import com.chzu.dao.TbGradeMapper;
import com.chzu.dao.TbTargetMapper;
import com.chzu.model.TbFile;
import com.chzu.model.TbGrade;
import com.chzu.model.TbTarget;
import com.chzu.model.bo.GradeInfo;
import com.chzu.service.GradeService;
import com.chzu.util.SmallUtil;
import com.chzu.util.TargetCalculationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("gradeService")
public class GradeServiceImpl implements GradeService {
    @Autowired
    private TbGradeMapper gradeMapper;
    @Autowired
    private TbTargetMapper targetMapper;
    @Autowired
    private TbFileMapper fileMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isGrade(String stuId) {
        Example example = new Example(TbGrade.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("studentId",stuId);
        TbGrade grade = gradeMapper.selectOneByExample(example);
        if (grade == null)
            return false;
        else
            return true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void gradeForStudent(TbGrade grade) {
        grade.setId(SmallUtil.getGeneratID());
        gradeMapper.insert(grade);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<GradeInfo> getGradeListByTeaId(String teaId) {
        return gradeMapper.getByTeaId(teaId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<GradeInfo> getGradeListByClaName(String claName) {
        return gradeMapper.getByClaName(claName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TbFile generateAchievementTable(List<GradeInfo> gradeInfoList) {
        List<TbTarget> tbTargets = targetMapper.selectAll();
        //查询课程目标的数据表格，生成分数和占比的数组
        TargetCalculationUtil.getRawData(tbTargets);

        //传入学生成绩数据，生成达成度表格，由此得到存储表格的相关信息
        TbFile file = TargetCalculationUtil.calculateGrade(gradeInfoList);

        //将文件信息存到数据库
        fileMapper.insert(file);

        return file;
    }
}
