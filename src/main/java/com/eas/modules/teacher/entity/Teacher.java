package com.eas.modules.teacher.entity;

import com.eas.common.persistence.BaseEntity;

/**
 * @Author llp
 * @Version 2018/10/19
 */
public class Teacher extends BaseEntity {
    private String tnum;
    private String tname;
    private String sex;
    private String academy;
    private String dept;
    private String ttel;
    private Integer TINVTIMES;

    public String getTnum() {
        return tnum;
    }

    public void setTnum(String tnum) {
        this.tnum = tnum;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getTtel() {
        return ttel;
    }

    public void setTtel(String ttel) {
        this.ttel = ttel;
    }

    public Integer getTINVTIMES() {
        return TINVTIMES;
    }

    public void setTINVTIMES(Integer TINVTIMES) {
        this.TINVTIMES = TINVTIMES;
    }
}
