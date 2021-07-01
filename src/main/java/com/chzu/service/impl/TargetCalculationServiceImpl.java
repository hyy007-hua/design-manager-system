package com.chzu.service.impl;

import com.chzu.dao.TbTargetMapper;
import com.chzu.model.TbTarget;
import com.chzu.model.bo.GradeInfo;
import com.chzu.service.TargetCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.util.List;

@Service("targetCalculationService")
public class TargetCalculationServiceImpl implements TargetCalculationService {
    @Autowired
    private TbTargetMapper targetMapper;

    @Override
    public File getTargetAchievementTable(List<GradeInfo> gradeList, String teaId) {
        Example example = new Example(TbTarget.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("upId",teaId);
        List<TbTarget> targetList = targetMapper.selectByExample(example);

        return null;
    }
}
