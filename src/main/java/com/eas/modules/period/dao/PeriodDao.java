package com.eas.modules.period.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.period.entity.Period;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lenovo
 * @Version 2018/10/20
 */
@MybatisDao
public interface PeriodDao extends CrudDao<Period> {
@Override
    public Period get(int id);
@Override
    List<Period> findAllList();
@Override
    public int delete(int id);
    @Override
    public int update(Period period);
    @Override
    int insert(Period period);

    @Override
    Period getT(Period entity);

    List<Period> findTimesByCondition(@Param(value="queryText")String queryText);

    List<Period> findListByText(@Param(value="queryText")String queryText);

    void deleteTimes(Integer[] ids);
}
