package com.chzu.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_grade")
public class TbGrade {
    @Id
    private String id;

    @Column(name = "grade_1")
    private Integer grade1;

    @Column(name = "grade_2")
    private Integer grade2;

    @Column(name = "grade_3")
    private Integer grade3;

    @Column(name = "grade_4")
    private Integer grade4;

    @Column(name = "grade_5")
    private Integer grade5;

    @Column(name = "student_id")
    private String studentId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return grade_1
     */
    public Integer getGrade1() {
        return grade1;
    }

    /**
     * @param grade1
     */
    public void setGrade1(Integer grade1) {
        this.grade1 = grade1;
    }

    /**
     * @return grade_2
     */
    public Integer getGrade2() {
        return grade2;
    }

    /**
     * @param grade2
     */
    public void setGrade2(Integer grade2) {
        this.grade2 = grade2;
    }

    /**
     * @return grade_3
     */
    public Integer getGrade3() {
        return grade3;
    }

    /**
     * @param grade3
     */
    public void setGrade3(Integer grade3) {
        this.grade3 = grade3;
    }

    /**
     * @return grade_4
     */
    public Integer getGrade4() {
        return grade4;
    }

    /**
     * @param grade4
     */
    public void setGrade4(Integer grade4) {
        this.grade4 = grade4;
    }

    /**
     * @return grade_5
     */
    public Integer getGrade5() {
        return grade5;
    }

    /**
     * @param grade5
     */
    public void setGrade5(Integer grade5) {
        this.grade5 = grade5;
    }

    /**
     * @return student_id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}