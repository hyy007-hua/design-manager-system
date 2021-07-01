package com.chzu.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_target")
public class TbTarget {
    @Id
    private String id;

    private String target;

    private Integer item;

    private Integer grade;

    private Integer proportion;

    @Column(name = "upId")
    private String upid;

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
     * @return target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return item
     */
    public Integer getItem() {
        return item;
    }

    /**
     * @param item
     */
    public void setItem(Integer item) {
        this.item = item;
    }

    /**
     * @return grade
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * @param grade
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * @return proportion
     */
    public Integer getProportion() {
        return proportion;
    }

    /**
     * @param proportion
     */
    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    /**
     * @return upId
     */
    public String getUpid() {
        return upid;
    }

    /**
     * @param upid
     */
    public void setUpid(String upid) {
        this.upid = upid;
    }
}