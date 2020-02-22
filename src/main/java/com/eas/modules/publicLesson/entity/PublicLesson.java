package com.eas.modules.publicLesson.entity;

import com.eas.common.persistence.BaseEntity;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.teacher.entity.Teacher;

import java.util.List;

public class PublicLesson extends BaseEntity {

    private Lesson lesson;//课程
    private AvailableRoom availableRoom;//可用教室
    private String tIds;//教师们的id，，，，
    private List<Teacher> teachers;//教师
    private List<Grade> grades;
    private String gids;

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public String getGids() {
        return gids;
    }

    public void setGids(String gids) {
        this.gids = gids;
    }

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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public String gettIds() {
        return tIds;
    }

    public void settIds(String tIds) {
        this.tIds = tIds;
    }
}
