package com.eas.modules.room.entity;

import com.eas.common.persistence.BaseEntity;
import com.eas.modules.building.entity.Building;

/**
 * @Author lenovo
 * @Version 2018/10/19
 */
public class Room extends BaseEntity {
    private String rnum;
    private Integer capacity;
    private Integer ttotalnum;
    private Building building;

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getTtotalnum() {
        return ttotalnum;
    }

    public void setTtotalnum(Integer ttotalnum) {
        this.ttotalnum = ttotalnum;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

}
