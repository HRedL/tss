package com.eas.modules.task.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.task.entity.Task;

import java.util.List;


@MybatisDao
public interface TaskDao extends CrudDao<Task> {

    @Override
    Task get(int id);

    @Override
    List<Task> findAllList();

    @Override
    int delete(int id);

    @Override
    int insert(Task task);

    @Override
    int update(Task task);

    @Override
    Task getT(Task task);


}
