package com.eas.modules.feedback.service;

import com.eas.common.persistence.CrudService;
import com.eas.modules.feedback.dao.FeedbackDao;
import com.eas.modules.feedback.entity.Feedback;
import com.eas.modules.user.dao.UserDao;
import com.eas.modules.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2019/6/10
 */
@Service
public class FeedbackService extends CrudService<FeedbackDao,Feedback> {
    @Autowired
    FeedbackDao feedbackDao;

    @Autowired
    UserDao userDao;

    @Override
    public Feedback get(int id) {

        return feedbackDao.get(id);
    }

    @Override
    public List<Feedback> findAllList() {
        return feedbackDao.findAllList();
    }

    @Override
    public int delete(int id) {
        return feedbackDao.delete(id);
    }

    @Override
    public int insert(Feedback feedback) {
        return feedbackDao.insert(feedback);
    }

    @Override
    public int update(Feedback feedback) {
        return feedbackDao.update(feedback);
    }

    @Override
    public Feedback getT(Feedback feedback){
        return feedbackDao.getT(feedback);
    }

    public List<Feedback> list(Map<String,Object> map,String logname) {
        User user=userDao.findUserByLogname(logname);
        Feedback feedback=new Feedback();
        feedback.setQuestionUser(user);
        map.put("feedback",feedback);
        List<Feedback> feedbacks=feedbackDao.getListCriteria(map);

        return feedbacks;
    }


    public int getCount(Map<String, Object> map, String logname) {
        User user=userDao.findUserByLogname(logname);
        Feedback feedback=new Feedback();
        feedback.setQuestionUser(user);
        map.put("feedback",feedback);
        return feedbackDao.getCountCriteria(map);
    }

    /**
     * 收到的反馈
     * @param map
     * @param logname
     * @return
     */
    public List<Feedback> list1(Map<String,Object> map,String logname) {
        User user=userDao.findUserByLogname(logname);
        Feedback feedback=new Feedback();
        feedback.setAnswerUser(user);
        map.put("feedback",feedback);
        List<Feedback> feedbacks=feedbackDao.getListCriteria(map);

        return feedbacks;
    }


    /**
     * 收到的反馈
     * @param map
     * @param logname
     * @return
     */
    public int getCount1(Map<String, Object> map, String logname) {
        User user=userDao.findUserByLogname(logname);
        Feedback feedback=new Feedback();
        feedback.setAnswerUser(user);
        map.put("feedback",feedback);
        return feedbackDao.getCountCriteria(map);
    }

    public int addSendFeedback(Feedback feedback) {
        User user1= userDao.findUserByLogname(feedback.getQuestionUser().getLogname());
        if(user1==null){
            return -1;
        }
        feedback.setQuestionUser(user1);
        User user2= userDao.findUserByLogname(feedback.getAnswerUser().getLogname());
        if(user2==null){
            return -2;
        }
        feedback.setAnswerUser(user2);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");

        feedback.setPeriod(dateFormat.format(date));
        feedback.setHasRead(false);

        return  feedbackDao.addSendFeedback(feedback);


    }
}
