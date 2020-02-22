package com.eas.modules.period.service;

import com.eas.common.persistence.CrudService;
import com.eas.modules.period.dao.PeriodDao;
import com.eas.modules.period.entity.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lenovo
 * @Version 2018/10/23
 */
@Service
public class PeriodService extends CrudService<PeriodDao,Period> {

    @Autowired
    PeriodDao periodDao;
    @Override
    public Period get(int id) {
        return super.get(id);
    }
    @Override
    public List<Period> findAllList() {return super.findAllList();}
    @Override
    public int delete(int id) {
        return super.delete(id);
    }
    @Override
    public int insert(Period period){return super.insert(period);}
    @Override
    public int update(Period period){return super.update(period);}

    public List<Period> findListByText(String queryText) {
        return periodDao.findListByText(queryText);
    }

    public void deleteTimes(Integer[] id) {
        periodDao.deleteTimes(id);
    }
}
