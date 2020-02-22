package com.eas.modules.teacher.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.dict.entity.Dict;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.student.entity.Student;
import com.eas.modules.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @Author llp
 * @Version 2018/10/20
 */
@MybatisDao
public interface TeacherDao extends CrudDao<Teacher> {

    /**
     * 根据教师号查找教师
     * @param tnum 教师号
     * @return 返回匹配的教师数据，如果没有匹配的数据，则返回null
     */
    Teacher findTeacherByTnum(String tnum);

    /**
     * 根据value和type查询label
     * @param dictValue dict中的value值
     * @param type dict中的type值
     * @return
     */
    Dict findLabelByValueAndType(
            @Param("dictValue") String dictValue,
            @Param("type") String type
    );

    /**
     * 根据tnum查找考次exam
     * @param tid 教师id
     * @return 返回该班级的所有考次
     */
    List<Exam> findExamsByTid(Integer tid);

    /**
     * 根据用户id查询用户信息
     * @param id 用户id
     * @return 返回与用户id匹配的用户数据，如果没有匹配的数据，则返回null
     */

    /**
     * 根据学号查询学生考试信息
     * @param snum 学生学号
     * @return 返回Exam
     */
    Exam getStuExamInfByLogname(String snum);

    @Override
    public Teacher get(int id);

    @Override
    public List<Teacher> findAllList();

    @Override
    int delete(int id);

    @Override
    int insert(Teacher teacher);

    @Override
    int update(Teacher teacher);

    @Override
    Teacher getT(Teacher teacher);

    List<Teacher> findTeacherByCondition(@Param(value = "queryText") String queryText);

    List<Teacher> findAllOrderByTINVTIMES();

    List<Teacher> findListByText(@Param(value = "queryText") String queryText);

    void deleteTeachers(Integer[] ids);

    List<Teacher> findAllListOrderByTInvtimes();

    List<Teacher> getTeacherByTids(List<Teacher> teachers);
}
