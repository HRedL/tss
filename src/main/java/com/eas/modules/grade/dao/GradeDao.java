package com.eas.modules.grade.dao;

import java.util.List;

import com.eas.common.annotation.MybatisDao;
import com.eas.modules.grade.entity.Grade;
import org.apache.ibatis.annotations.Param;

@MybatisDao
public interface GradeDao {
	
	/**
	 * 查询所有班级信息
	 * @return
	 */
	List<Grade> findAllGrade();
	
	/**
	 * 根据班级id删除数据
	 * @param id 被删除的班级的id
	 * @return 返回受影响的行数
	 */
	Integer delete(Integer id);
	
	/**
	 * 根据id查询班级信息
	 * @param id
	 * @return 与id匹配的班级数据，如果没有匹配的数据，则返回null
	 */
	Grade findGradeById(Integer id);
	
	/**
	 * 根据班级id修改班级信息
	 * @param grade 应该封装了被修改的班级的id，和需要修改的新的数据。
	 * @return 返回受影响的行数
	 */
	Integer update(Grade grade);
	
	/**
	 * 新增班级信息
	 * @param grade
	 * @return 返回受影响的行数，如果需要获取班级的id，
	 * 	可以在调用本方法后，从参数对象中通过getID()方法获取。
	 */
	Integer createGrade(Grade grade);


	Grade getT(Grade grade);

	List<Grade> findGradesByCondition(@Param(value="queryText")String  queryText);

	List<Grade> findListByText(@Param(value = "queryText") String queryText);

	void deleteGrades(Integer[] ids);

    int insert(Grade grade);

    List<Grade> getGradesByAcademyAndDept(Grade grade);
}
