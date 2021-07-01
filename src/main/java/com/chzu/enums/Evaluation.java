package com.chzu.enums;

/**
 * 教师评价学生成绩的各个方面，课内实践，团队合作，答辩，系统实现，实践报告
 */
public enum Evaluation {
    PRACTICE(0,"课内实践"),
    COOPERATION(1,"团队合作"),
    REPLY(2,"答辩"),
    SYSTEM_IMPLEMENTATION(3,"系统实现"),
    PRACTICE_REPORT(4,"实践报告");

    public final Integer type;
    public final String value;
    Evaluation(Integer type,String value){
        this.type = type;
        this.value  = value;
    }
}
