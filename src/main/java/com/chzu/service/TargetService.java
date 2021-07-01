package com.chzu.service;

import com.chzu.model.vo.TargetVO;

public interface TargetService {

    /**
     * 添加课程目标
     * @param target
     * @param upId
     */
    public void addObjective(TargetVO target, String upId);
}
