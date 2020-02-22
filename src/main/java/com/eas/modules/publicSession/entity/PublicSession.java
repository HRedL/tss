package com.eas.modules.publicSession.entity;

import com.eas.common.persistence.BaseEntity;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.teacher.entity.Teacher;

import java.util.List;

/**
 * @Author lenovo
 * @Version 2018/10/23
 */

public class PublicSession extends BaseEntity {


    private Lesson lesson;
    private AvailableRoom availableRoom;
    private String tcount;
    private List<Teacher> teachers;

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public AvailableRoom getAvailableRoom() {
        return availableRoom;
    }

    public void setAvailableRoom(AvailableRoom availableRoom) {
        this.availableRoom = availableRoom;
    }

    public String getTcount() {
        return tcount;
    }

    public void setTcount(String tcount) {
        this.tcount = tcount;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
