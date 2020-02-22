package com.eas.modules.availableRoom.entity;

import com.eas.common.persistence.BaseEntity;
import com.eas.modules.period.entity.Period;
import com.eas.modules.room.entity.Room;
import com.eas.modules.week.entity.Week;

/**
 * @Author lenovo
 * @Version 2018/10/19
 */
public class AvailableRoom extends BaseEntity {
    private Room room;
    private Week week;
    private Period period;
    private Integer flag;
    private Integer sortFlag=0;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
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
