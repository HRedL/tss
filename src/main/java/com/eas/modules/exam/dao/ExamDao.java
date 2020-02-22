package com.eas.modules.exam.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.exam.entity.Exam;
import java.util.List;
import java.util.Map;

/**
 * @Author hhl
 * @Version 2018/10/20
 */
@MybatisDao
public interface ExamDao extends CrudDao<Exam> {

    @Override
    public Exam get(int id);




    @Override
    public List<Exam> findAllList();


    @Override
    public int delete(int id);


    @Override
    int insert(Exam exam);


    @Override
    int update(Exam exam);

    void deleteExams(Integer[] ids);


    List<Exam> getExams(Exam exam);


    @Override
    Exam getT(Exam exam);

    int insertExams(List<Exam> exams);

    List<Exam> getTInf(Exam exam);

    List<Exam> getSInf(Exam exam);

    List<Exam> findExamsByCondition(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    int deleteAllExams();

    List<Exam> findAllList1();
}
