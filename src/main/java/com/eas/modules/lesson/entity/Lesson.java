package com.eas.modules.lesson.entity;

import com.eas.common.persistence.BaseEntity;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.teacher.entity.Teacher;

import java.util.List;

/**
 * @Author hhl
 * @Version 2018/10/19
 */
public class Lesson extends BaseEntity {
    private String lnum;       //课程号
    private String subject;    //科目
    private Integer ltotalnum; //课程总人数
    private String type;       //课程类型
    private String gIds;       //这个是自己查询
    private List<Grade> grades;//班级
    private Teacher teacher;   //授课老师
    private Integer flag;
    private Integer sortFlag=0;


    @Override
    public String toString() {
        return "Lesson{" +
                "lnum='" + lnum + '\'' +
                ", subject='" + subject + '\'' +
                ", ltotalnum=" + ltotalnum +
                ", type='" + type + '\'' +
                ", gIds='" + gIds + '\'' +
                ", grades=" + grades +
                ", teacher=" + teacher +
                '}';
    }

    public String getLnum() {
        return lnum;
    }

    public void setLnum(String lnum) {
        this.lnum = lnum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getLtotalnum() {
        return ltotalnum;
    }

    public void setLtotalnum(Integer ltotalnum) {
        this.ltotalnum = ltotalnum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getgIds() {
        return gIds;
    }

    public void setgIds(String gIds) {
        this.gIds = gIds;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getSortFlag() {
        return sortFlag;
    }

    public void setSortFlag(Integer sortFlag) {
        this.sortFlag = sortFlag;
    }
}
