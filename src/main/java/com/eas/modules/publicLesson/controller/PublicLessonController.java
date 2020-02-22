package com.eas.modules.publicLesson.controller;

import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.availableRoom.service.AvailableRoomService;
import com.eas.modules.publicLesson.entity.PublicLesson;
import com.eas.modules.publicLesson.service.PublicLessonService;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.lesson.service.LessonService;
import com.eas.modules.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("publicLesson")
public class PublicLessonController extends BaseController {

    @Autowired
    PublicLessonService publicLessonService;

    @Autowired
    AvailableRoomService availableRoomService;
    @Autowired
    LessonService lessonService;

    @Autowired
    TeacherService teacherService;

    @ResponseBody
    @GetMapping("/criteriaQuery")
    public AjaxJson criteriaQuery(Integer pageNumber, Integer pageSize, Integer weekId, Integer timeId, Integer roomId, Integer lessonId){
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
            map.put("weekId",weekId);
            map.put("timeId",timeId);
            map.put("roomId",roomId);
            map.put("lessonId",lessonId);

            List<PublicLesson> publicLessons=publicLessonService.findPublicLessonByCondition(map);

            PageInfo pageInfo=new PageInfo();
            //总页码
            int totalNumber=0;
            //当前的数据条数
            int totalSize=publicLessonService.getCount(map);
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
            }else if(totalNumber==0){
                pageInfo.setNavigatepageNums(new int[]{});
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

            pageInfo.setList(publicLessons);
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
    @PostMapping("/updatePublicLesson")
    public AjaxJson updatePublicLesson(Integer id, Integer availableRoomId, Integer lessonId){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            PublicLesson publicLesson=new PublicLesson();
            AvailableRoom availableRoom=new AvailableRoom();
            availableRoom.setId(availableRoomId);
            Lesson lesson=new Lesson();
            lesson.setId(lessonId);
            publicLesson.setId(id);
            publicLesson.setAvailableRoom(availableRoom);
            publicLesson.setLesson(lesson);
            publicLessonService.update(publicLesson);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }

        return ajaxJson;
    }


    @ResponseBody
    @GetMapping("/getPublicLesson")
    public AjaxJson getPublicLesson(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            PublicLesson publicLesson=publicLessonService.get(id);
            ajaxJson.getBody().put("publicLesson",publicLesson);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


    @ResponseBody
    @PostMapping("/addPublicLesson")
    public AjaxJson addPublicLesson(Integer availableRoomId,Integer lessonId){
        AjaxJson ajaxJson=new AjaxJson();
        PublicLesson publicLesson=new PublicLesson();
        AvailableRoom availableRoom=new AvailableRoom();
        Lesson lesson=new Lesson();
        availableRoom.setId(availableRoomId);
        lesson.setId(lessonId);
        publicLesson.setLesson(lesson);
        publicLesson.setAvailableRoom(availableRoom);
        try{
            if(publicLessonService.getT(publicLesson)==null){
                publicLessonService.insert(publicLesson);
                ajaxJson.setSuccess(true);}
            else {
                ajaxJson.setMsg("该公共课已存在");
                ajaxJson.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    //将公共课表插入到考次表中
    @ResponseBody
    @PostMapping("/insertToExam")
    public AjaxJson insertToExam(PublicLesson publicLesson){
        AjaxJson ajaxJson=new AjaxJson();
        try{
                publicLessonService.insertToExam(publicLesson);
                ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }



    @ResponseBody
    @PostMapping("/validatePublicLesson")
    public AjaxJson validatePublicLesson(Integer availableRoomId){
        AjaxJson ajaxJson=new AjaxJson();
        PublicLesson publicLesson=new PublicLesson();
        AvailableRoom availableRoom=new AvailableRoom();
        availableRoom.setId(availableRoomId);
        publicLesson.setAvailableRoom(availableRoom);
        try{
            PublicLesson publicLesson1=publicLessonService.getT(publicLesson);
            if(publicLesson1!=null){
                ajaxJson.getBody().put("publicLesson",publicLesson1);
                ajaxJson.setMsg("该可用教室已被占用");
                ajaxJson.setSuccess(false);
            }else{
                ajaxJson.setMsg("验证成功");
                ajaxJson.setSuccess(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("验证失败");
        }
        return ajaxJson;
    }


    @ResponseBody
    @GetMapping("/detail")
    public AjaxJson getDetail(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try {
            PublicLesson publicLesson=publicLessonService.get(id);
            ajaxJson.getBody().put("publicLesson",publicLesson);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public AjaxJson delete(Integer id){
        AjaxJson ajaxJson=new AjaxJson();

        try{
           publicLessonService.delete(id);
           ajaxJson.setSuccess(true);
           ajaxJson.setMsg("删除成功");

        }catch(Exception e){
           e.printStackTrace();
           ajaxJson.setSuccess(false);
           ajaxJson.setMsg("删除失败!");
        }
        return ajaxJson;
    }

    @ResponseBody
    @RequestMapping("/deletePublicLessons")
    public AjaxJson deleteLessons(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();

        try{
            publicLessonService.deletePublicLessons(id);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("删除失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/validateExisitPublicLesson")
    public AjaxJson validateExisitPublicLesson(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            boolean flag=publicLessonService.vailidateExisitPublicLesson();
            ajaxJson.getBody().put("flag",flag);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }

        return ajaxJson;
    }


    @ResponseBody
    @GetMapping("/test")
    public List<List<Lesson>> test(){
        return publicLessonService.test();
    }

    @ResponseBody
    @GetMapping("/deleteAll")
    public AjaxJson deleteAll(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            publicLessonService.deleteAllPublicLessons();
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @RequestMapping("/updateRoom")
    public AjaxJson updateAllRoom(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            publicLessonService.updateRoom();
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("失败");
        }
        return ajaxJson;
    }
}
