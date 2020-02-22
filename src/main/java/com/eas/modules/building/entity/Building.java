package com.eas.modules.building.entity;

import com.eas.common.persistence.BaseEntity;

/**
 * @Author lenovo
 * @Version 2018/10/19
 */
public class Building extends BaseEntity {
    private String campus;
    private String bnum;

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getBnum() {
        return bnum;
    }

    public void setBnum(String bnum) {
        this.bnum = bnum;
    }

}
