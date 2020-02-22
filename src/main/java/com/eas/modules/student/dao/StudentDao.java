package com.eas.modules.student.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.dict.entity.Dict;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.student.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author llp
 * @Version 2018/10/22
 */
@MybatisDao
public interface StudentDao extends CrudDao<Student> {

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
     * 根据gid查询班级
     * @param gid 班级id
     * @return 返回grade对象
     */
    Grade findGradeByGid(Integer gid);

    /**
     * 根据gid查找考次exam
     * @param gid 班级id
     * @return 返回该班级的所有考次
     */
    List<Exam> findExamsByGid(Integer gid);

    /**
     * 根据学号查找学生
     * @param snum 学号
     * @return 返回匹配的学生数据，如果没有匹配的数据，则返回null
     */
    Student findStudentBySnum(String snum);

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

    /*Grade getInfo(String snum);*/

    @Override
    public Student get(int id);

    @Override
    public List<Student> findAllList();

    @Override
    int delete(int id);

    @Override
    int insert(Student student);

    @Override
    int update(Student student);

    @Override
    Student getT(Student student);

    List<Student> findStudentByCondition(@Param(value = "queryText") String queryText);

    void deleteStudents(Integer[] id);

    List<Student> findListByText(@Param(value = "queryText") String queryText);
}
