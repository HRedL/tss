package com.eas.modules.week.dao;

import java.util.List;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.week.entity.Week;
import org.apache.ibatis.annotations.Param;

@MybatisDao
public interface WeekDao extends CrudDao<Week> {


	@Override
	Week get(int id);


	@Override
	Week getT(Week week);




	@Override
	int insert(Week entity);

	@Override
	int update(Week week);

	@Override
	int delete(int id);


    //List<Week> findWeeksByCondition(@Param(value = "queryText") String queryText);

	List<Week> findListByText(@Param(value="queryText")String queryText);

	int deleteWeeks(Integer[] id);


	@Override
	List<Week> findAllList();
}


