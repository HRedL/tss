package com.eas.modules.student.entity;

import com.eas.common.persistence.BaseEntity;
import com.eas.modules.grade.entity.Grade;

/**
 * @Author llp
 * @Version 2018/10/22
 */
public class Student extends BaseEntity {

    private String snum;
    private String sname;
    private String sex;
    private Grade grade;
    private String stel;
    private Integer gid;
    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }


    public String getSnum() {
        return snum;
    }

    public void setSnum(String snum) {
        this.snum = snum;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getStel() {
        return stel;
    }

    public void setStel(String stel) {
        this.stel = stel;
    }

}

