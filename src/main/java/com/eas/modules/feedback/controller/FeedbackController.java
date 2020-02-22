package com.eas.modules.feedback.controller;

import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.modules.feedback.entity.Feedback;
import com.eas.modules.feedback.service.FeedbackService;
import com.eas.modules.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2019/6/10
 */
@RequestMapping("feedback")
@Controller
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @ResponseBody
    @GetMapping("/get")
    public AjaxJson get(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Feedback feedback=feedbackService.get(id);
            ajaxJson.getBody().put("feedback",feedback);
            ajaxJson.setMsg("查询成功");
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("查询失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/getT")
    public AjaxJson getT(Feedback feedback){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Feedback feedback1=feedbackService.getT(feedback);
            ajaxJson.getBody().put("feedback",feedback1);
            ajaxJson.setMsg("查询成功");
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("查询失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


    @ResponseBody
    @RequestMapping("/list")
    public AjaxJson list(Integer pageNumber,Integer pageSize,String logname){
        if(pageNumber==null){
            pageNumber=1;
        }
        if(pageSize==null){
            pageSize=20;
        }
        AjaxJson ajaxJson=new AjaxJson();
        try{

            Map<String ,Object> map=new HashMap();
            map.put("start",(pageNumber-1)*pageSize);
            map.put("pageSize",pageSize);
            //就是这一块不太行啊
            List<Feedback> feedbacks=feedbackService.list(map,logname);

            PageInfo pageInfo=new PageInfo();
            //总页码
            int totalNumber=0;
            //当前的数据条数
            int totalSize=feedbackService.getCount(map,logname);
            //如果正好整除，总页码=总记录/每页记录数,不正好，那么，总页码=总记录/每页记录数+1
            if(totalSize%pageSize==0){
                totalNumber=totalSize/pageSize;
            }else {
                totalNumber=totalSize/pageSize+1;
            }
            //设置是否有前一页或后一页
            if(pageNumber==1){
                pageInfo.setHasPreviousPage(false);
            }else{
                pageInfo.setHasPreviousPage(true);
            }
            if(pageNumber==totalNumber){
                pageInfo.setHasNextPage(false);
            }else{
                pageInfo.setHasNextPage(true);
            }

            //填充页面分页条的数字
            if(totalNumber==1){
                pageInfo.setNavigatepageNums(new int[]{1});
            }else if(totalNumber==2){
                pageInfo.setNavigatepageNums(new int[]{1,2});
            }else if(totalNumber==3){
                pageInfo.setNavigatepageNums(new int[]{1,2,3});
            }else if(totalNumber==4){
                pageInfo.setNavigatepageNums(new int[]{1,2,3,4});
            }else if(pageNumber<=3){
                pageInfo.setNavigatepageNums(new int[]{1,2,3,4,5});
            }else if(pageNumber+3>totalNumber){
                int[] array=new int[5];
                array[0]=totalNumber-4;
                array[1]=totalNumber-3;
                array[2]=totalNumber-2;
                array[3]=totalNumber-1;
                array[4]=totalNumber;
                pageInfo.setNavigatepageNums(array);
            }else{
                int[] array=new int[5];
                array[0]=pageNumber-2;
                array[1]=pageNumber-1;
                array[2]=pageNumber;
                array[3]=pageNumber+1;
                array[4]=pageNumber+2;
                pageInfo.setNavigatepageNums(array);
            }

            pageInfo.setList(feedbacks);
            //设置页面中显示多少条数据
            pageInfo.setPageSize(pageSize);

            pageInfo.setPages(totalNumber);
            ajaxJson.put("pageInfo",pageInfo);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("查询成功");
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("查询失败");
        }
        return ajaxJson;

    }


    @ResponseBody
    @RequestMapping("/list1")
    public AjaxJson list1(Integer pageNumber,Integer pageSize,String logname){
        if(pageNumber==null){
            pageNumber=1;
        }
        if(pageSize==null){
            pageSize=20;
        }
        AjaxJson ajaxJson=new AjaxJson();
        try{

            Map<String ,Object> map=new HashMap();
            map.put("start",(pageNumber-1)*pageSize);
            map.put("pageSize",pageSize);
            //就是这一块不太行啊
            List<Feedback> feedbacks=feedbackService.list1(map,logname);



            PageInfo pageInfo=new PageInfo();
            //总页码
            int totalNumber=0;
            //当前的数据条数
            int totalSize=feedbackService.getCount1(map,logname);
            //如果正好整除，总页码=总记录/每页记录数,不正好，那么，总页码=总记录/每页记录数+1
            if(totalSize%pageSize==0){
                totalNumber=totalSize/pageSize;
            }else {
                totalNumber=totalSize/pageSize+1;
            }
            //设置是否有前一页或后一页
            if(pageNumber==1){
                pageInfo.setHasPreviousPage(false);
            }else{
                pageInfo.setHasPreviousPage(true);
            }
            if(pageNumber==totalNumber){
                pageInfo.setHasNextPage(false);
            }else{
                pageInfo.setHasNextPage(true);
            }

            //填充页面分页条的数字
            if(totalNumber==1){
                pageInfo.setNavigatepageNums(new int[]{1});
            }else if(totalNumber==2){
                pageInfo.setNavigatepageNums(new int[]{1,2});
            }else if(totalNumber==3){
                pageInfo.setNavigatepageNums(new int[]{1,2,3});
            }else if(totalNumber==4){
                pageInfo.setNavigatepageNums(new int[]{1,2,3,4});
            }else if(pageNumber<=3){
                pageInfo.setNavigatepageNums(new int[]{1,2,3,4,5});
            }else if(pageNumber+3>totalNumber){
                int[] array=new int[5];
                array[0]=totalNumber-4;
                array[1]=totalNumber-3;
                array[2]=totalNumber-2;
                array[3]=totalNumber-1;
                array[4]=totalNumber;
                pageInfo.setNavigatepageNums(array);
            }else{
                int[] array=new int[5];
                array[0]=pageNumber-2;
                array[1]=pageNumber-1;
                array[2]=pageNumber;
                array[3]=pageNumber+1;
                array[4]=pageNumber+2;
                pageInfo.setNavigatepageNums(array);
            }
            pageInfo.setList(feedbacks);
            //设置页面中显示多少条数据
            pageInfo.setPageSize(pageSize);

            pageInfo.setPages(totalNumber);
            ajaxJson.put("pageInfo",pageInfo);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("查询成功");
        }catch(Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("查询失败");
        }
        return ajaxJson;

    }

    @ResponseBody
    @GetMapping("/findAllList")
    public AjaxJson findAllList(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Feedback> feedbacks= feedbackService.findAllList();

            ajaxJson.getBody().put("feedbacks",feedbacks);
            ajaxJson.setMsg("查询成功");
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("查询失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;

    }


    @ResponseBody
    @PostMapping("/delete")
    public AjaxJson delete(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            feedbackService.delete(id);
            ajaxJson.setMsg("删除成功");
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("删除失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;

    }

    @ResponseBody
    @PostMapping(value = "/update")
    public AjaxJson update(Feedback feedback) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            feedbackService.update(feedback);
            ajaxJson.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("修改失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/replyFeedback")
    public AjaxJson replyFeedback(Feedback feedback) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            feedbackService.update(feedback);
            ajaxJson.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("修改失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/add")
    public AjaxJson add(Feedback feedback){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            feedbackService.insert(feedback);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/sendFeedback")
    public AjaxJson addSendFeedback(String questionUserLogname,String answerUserLogname,String title,String question){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            User user1=new User();
            user1.setLogname(questionUserLogname);
            User user2=new User();
            user2.setLogname(answerUserLogname);
            Feedback feedback=new Feedback();
            feedback.setQuestionUser(user1);
            feedback.setAnswerUser(user2);
            feedback.setTitle(title);
            feedback.setQuestion(question);
            int flag= feedbackService.addSendFeedback(feedback);
            if(flag==-1){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无效发件人");
            }else if(flag==-2){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("无效收件人");
            }else{
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("发送成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加失败");
        }

        return ajaxJson;
    }

}
