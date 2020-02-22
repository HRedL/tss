package com.eas.modules.lessonGroup.entity;

import com.eas.common.persistence.BaseEntity;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.lesson.entity.Lesson;

/**
 * @Author lenovo
 * @Version 2018/10/23
 */
public class LessonGroup extends BaseEntity {

    private Lesson lesson;
    private Grade grade;

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Grade getGroup() {
        return grade;
    }

    public void setGroup(Grade grade) {
        this.grade = grade;
    }
}
