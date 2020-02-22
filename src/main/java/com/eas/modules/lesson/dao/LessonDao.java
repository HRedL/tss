package com.eas.modules.lesson.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.lesson.entity.Lesson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author hhl
 * @Version 2018/10/20
 */
@MybatisDao
public interface LessonDao extends CrudDao<Lesson> {

    @Override
    public Lesson get(int id);


    @Override
    public List<Lesson> findAllList();

    @Override
    public int delete(int id);

    @Override
    int insert(Lesson lesson);


    @Override
    int update(Lesson lesson);

    List<Lesson> findLessonsByCondition(@Param(value = "queryText") String queryText);


    @Override
    Lesson getT(Lesson lesson);

    void deleteLessons(Integer[] ids);

    List<Lesson> findListByText(@Param(value = "queryText") String queryText);

    Lesson getLessonByLnum(@Param("lnum") String lnum);

    @Override
    List<Lesson> findList(Lesson entity);

    List<String> findLessonNumberList();

    int bacthInset(List<Lesson> lessons);

    List<Lesson> findLessonsByIds(List<Integer> lessonIds);

    List<Lesson> findAllLessonWithAllInfo();
}
