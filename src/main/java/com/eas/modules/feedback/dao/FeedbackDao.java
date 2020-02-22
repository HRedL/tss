package com.eas.modules.feedback.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.feedback.entity.Feedback;

import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2019/6/10
 */
@MybatisDao
public interface FeedbackDao extends CrudDao<Feedback> {

    @Override
    Feedback get(int id);

    @Override
    List<Feedback> findAllList();

    @Override
    int delete(int id);

    @Override
    int insert(Feedback feedback);

    @Override
    int update(Feedback feedback);

    @Override
    Feedback getT(Feedback feedback);

    List<Feedback> getList(Feedback feedback);

    List<Feedback> getListCriteria(Map<String, Object> map);

    Integer getCountCriteria(Map<String, Object> map);

    int addSendFeedback(Feedback feedback);
}
