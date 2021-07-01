package com.chzu.model.bo;

public class GradeInfo {
    private String id;
    private Integer grade1;
    private Integer grade2;
    private Integer grade3;
    private Integer grade4;
    private Integer grade5;
    private String name;
    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getGrade1() {
        return grade1;
    }

    public void setGrade1(Integer grade1) {
        this.grade1 = grade1;
    }

    public Integer getGrade2() {
        return grade2;
    }

    public void setGrade2(Integer grade2) {
        this.grade2 = grade2;
    }

    public Integer getGrade3() {
        return grade3;
    }

    public void setGrade3(Integer grade3) {
        this.grade3 = grade3;
    }

    public Integer getGrade4() {
        return grade4;
    }

    public void setGrade4(Integer grade4) {
        this.grade4 = grade4;
    }

    public Integer getGrade5() {
        return grade5;
    }

    public void setGrade5(Integer grade5) {
        this.grade5 = grade5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "GradeInfo{" +
                "id='" + id + '\'' +
                ", grade1=" + grade1 +
                ", grade2=" + grade2 +
                ", grade3=" + grade3 +
                ", grade4=" + grade4 +
                ", grade5=" + grade5 +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
