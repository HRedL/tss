package com.eas.modules.period.entity;

import com.eas.common.persistence.BaseEntity;

/**
 * @Author lenovo
 * @Version 2018/10/19
 */
public class Period extends BaseEntity {
    private String starttime;
    private String endtime;

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
