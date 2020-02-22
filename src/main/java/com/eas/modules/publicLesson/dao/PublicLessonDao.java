package com.eas.modules.publicLesson.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.publicLesson.entity.PublicLesson;

import java.util.List;
import java.util.Map;

@MybatisDao
public interface PublicLessonDao extends CrudDao<PublicLesson> {

    @Override
    public PublicLesson get(int id);

    @Override
    public List<PublicLesson> findAllList();

    @Override
    public int delete(int id);


    @Override
    int insert(PublicLesson publicLesson);

    int insertToExam(PublicLesson publicLesson);

    @Override
    int update(PublicLesson publicLesson);

    void deletePublicLessons(Integer[] ids);

    List<PublicLesson> getPublicLessons(PublicLesson publicLesson);

    @Override
    PublicLesson getT(PublicLesson publicLesson);

    int insertPublicLessons(List<PublicLesson> publicLessons);

    List<PublicLesson> getRoom(List<PublicLesson> publicLessons);

    List<PublicLesson> findPublicLessonsByCondition(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    int deleteAllPublicLessons();

    int batchInsert(List<PublicLesson> publicLessons);
}
