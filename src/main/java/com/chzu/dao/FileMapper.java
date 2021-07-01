package com.chzu.dao;

import com.chzu.model.bo.ReportFileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileMapper {
    public List<ReportFileInfo> getReportInfo(@Param("claId") String claId);
    public List<ReportFileInfo> getReportInfoByClaNameOrNumber(@Param("claName") String claName,@Param("number") String number);
}
