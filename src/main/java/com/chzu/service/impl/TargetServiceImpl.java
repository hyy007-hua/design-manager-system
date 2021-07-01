package com.chzu.service.impl;

import com.chzu.dao.TbTargetMapper;
import com.chzu.enums.Evaluation;
import com.chzu.model.TbTarget;
import com.chzu.model.vo.TargetVO;
import com.chzu.service.TargetService;
import com.chzu.util.SmallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("targetService")
public class TargetServiceImpl implements TargetService {
    @Autowired
    private TbTargetMapper targetMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addObjective(TargetVO target, String upId) {
        TbTarget tbTarget1 = new TbTarget();
        tbTarget1.setUpid(upId);
        tbTarget1.setTarget(target.getObject1());

        tbTarget1.setId(SmallUtil.getGeneratID());
        tbTarget1.setItem(Evaluation.PRACTICE.type);
        tbTarget1.setGrade(Integer.parseInt(target.getParameter1()));
        tbTarget1.setProportion(Integer.parseInt(target.getProportion1()));
        targetMapper.insert(tbTarget1);

        tbTarget1.setId(SmallUtil.getGeneratID());
        tbTarget1.setItem(Evaluation.COOPERATION.type);
        tbTarget1.setGrade(Integer.parseInt(target.getParameter2()));
        tbTarget1.setProportion(Integer.parseInt(target.getProportion2()));
        targetMapper.insert(tbTarget1);

        tbTarget1.setId(SmallUtil.getGeneratID());
        tbTarget1.setItem(Evaluation.REPLY.type);
        tbTarget1.setGrade(Integer.parseInt(target.getParameter3()));
        tbTarget1.setProportion(Integer.parseInt(target.getProportion3()));
        targetMapper.insert(tbTarget1);

        tbTarget1.setId(SmallUtil.getGeneratID());
        tbTarget1.setItem(Evaluation.SYSTEM_IMPLEMENTATION.type);
        tbTarget1.setGrade(Integer.parseInt(target.getParameter4()));
        tbTarget1.setProportion(Integer.parseInt(target.getProportion4()));
        targetMapper.insert(tbTarget1);

        tbTarget1.setId(SmallUtil.getGeneratID());
        tbTarget1.setItem(Evaluation.PRACTICE_REPORT.type);
        tbTarget1.setGrade(Integer.parseInt(target.getParameter5()));
        tbTarget1.setProportion(Integer.parseInt(target.getProportion5()));
        targetMapper.insert(tbTarget1);

    }
}
