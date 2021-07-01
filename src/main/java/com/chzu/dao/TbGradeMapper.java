package com.chzu.dao;

import com.chzu.model.bo.GradeInfo;
import com.chzu.util.mymapper.MyMapper;
import com.chzu.model.TbGrade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbGradeMapper extends MyMapper<TbGrade> {
    public List<GradeInfo> getByTeaId(String teaId);

    public List<GradeInfo> getByClaName(String claName);
}