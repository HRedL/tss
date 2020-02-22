package com.eas.modules.lessonGroup.dao;

import com.eas.common.persistence.CrudDao;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.lessonGroup.entity.LessonGroup;

import java.util.List;

/**
 * @Author lenovo
 * @Version 2018/10/23
 */
public interface LessonGroupDao extends CrudDao<LessonGroup>{


    public List<Grade> getGrades(int lId);



}
