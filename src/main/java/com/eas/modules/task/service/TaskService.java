package com.eas.modules.task.service;

import com.eas.common.persistence.CrudService;

import com.eas.modules.task.dao.TaskDao;
import com.eas.modules.task.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService extends CrudService<TaskDao,Task> {

    @Autowired
    TaskDao taskDao;


    @Override
    public Task get(int id) {

        return taskDao.get(id);
    }

    @Override
    public List<Task> findAllList() {
        return taskDao.findAllList();
    }

    @Override
    public int delete(int id) {
        return taskDao.delete(id);
    }

    @Override
    public int insert(Task task) {
        return taskDao.insert(task);
    }

    @Override
    public int update(Task task) {
        return taskDao.update(task);
    }

    @Override
    public Task getT(Task task){
        return taskDao.getT(task);
    }
}
