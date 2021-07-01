package com.chzu.service;

import com.chzu.model.bo.GradeInfo;

import java.io.File;
import java.util.List;

public interface TargetCalculationService {
    File getTargetAchievementTable(List<GradeInfo> gradeList,String teaId);
}
