package com.eas.modules.week.service;

import com.eas.common.persistence.CrudService;
import com.eas.modules.week.dao.WeekDao;
import com.eas.modules.week.entity.Week;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author huahonglei
 * @Version 2019/4/10
 */
@Service
public class WeekService extends CrudService<WeekDao,Week> {

    @Autowired
    private WeekDao weekDao;


    @Override
    public Week get(int id) {
        return super.get(id);
    }
    @Override
    public List<Week> findAllList() {return super.findAllList();}
    @Override
    public int delete(int id) {
        return super.delete(id);
    }
    @Override
    public int insert(Week week){return super.insert(week);}
    @Override
    public int update(Week week){return super.update(week);}

    public List<Week> findListByText(String queryText) {
        return weekDao.findListByText(queryText);
    }

    public void deleteWeeks(Integer[] id) {
        weekDao.deleteWeeks(id);
    }



}
