package com.chzu.model.bo;

public class ReportFileInfo {
    private String id;
    private String fileName;
    private String name;

    public ReportFileInfo() {
    }

    public ReportFileInfo(String id, String fileName, String name) {
        this.id = id;
        this.fileName = fileName;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
