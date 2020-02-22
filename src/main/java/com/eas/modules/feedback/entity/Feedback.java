package com.eas.modules.feedback.entity;

import com.eas.common.persistence.BaseEntity;
import com.eas.modules.user.entity.User;

/**
 * @Author huahonglei
 * @Version 2019/6/10
 */
public class Feedback extends BaseEntity {
     private Integer id;
     private String period;
     private String question;
     private String answer;
     private String title;
     private Boolean hasRead;
     private User questionUser;
     private User answerUser;


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public User getQuestionUser() {
        return questionUser;
    }

    public void setQuestionUser(User questionUser) {
        this.questionUser = questionUser;
    }

    public User getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(User answerUser) {
        this.answerUser = answerUser;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
