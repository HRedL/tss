package com.eas.modules.exam.controller;

import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.availableRoom.service.AvailableRoomService;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.exam.service.ExamService1;
import com.eas.modules.exam.service.ExamService_llz;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.lesson.service.LessonService;
import com.eas.modules.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hhl
 * @Version 2018/10/20
 */
@Controller
@RequestMapping("exam")
public class ExamController extends BaseController {

    @Autowired
    ExamService1 examService;
    @Autowired
    ExamService_llz examService_llz;

    @Autowired
    AvailableRoomService availableRoomService;
    @Autowired
    LessonService lessonService;

    @Autowired
    TeacherService teacherService;

    @ResponseBody
    @GetMapping("/criteriaQuery")
    public AjaxJson criteriaQuery(Integer pageNumber, Integer pageSize, Integer weekId, Integer timeId, Integer roomId, Integer lessonId, Integer teacherId){
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
            map.put("periodId",timeId);
            map.put("roomId",roomId);
            map.put("lessonId",lessonId);
            map.put("teacherId",teacherId);

            List<Exam> exams=examService.findExamByCondition(map);

            PageInfo pageInfo=new PageInfo();
            //总页码
            int totalNumber=0;
            //当前的数据条数
            int totalSize=examService.getCount(map);
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

            pageInfo.setList(exams);
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
    @PostMapping("/updateExam")
    public AjaxJson updateExam(Integer id, Integer availableRoomId, Integer lessonId, String tIds){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Exam exam=new Exam();
            AvailableRoom availableRoom=new AvailableRoom();
            availableRoom.setId(availableRoomId);
            Lesson lesson=new Lesson();
            lesson.setId(lessonId);
            exam.setId(id);
            exam.setAvailableRoom(availableRoom);
            exam.setLesson(lesson);
            exam.settIds(tIds);
            examService.update(exam);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }

        return ajaxJson;
    }


    @ResponseBody
    @GetMapping("/getExam")
    public AjaxJson getExam(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Exam exam=examService.get(id);
            ajaxJson.getBody().put("exam",exam);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


    @ResponseBody
    @PostMapping("/addExam")
    public AjaxJson addExam(Integer availableRoomId,Integer lessonId,String tIds){
        AjaxJson ajaxJson=new AjaxJson();
        Exam exam=new Exam();
        AvailableRoom availableRoom=new AvailableRoom();
        Lesson lesson=new Lesson();
        availableRoom.setId(availableRoomId);
        lesson.setId(lessonId);
        exam.settIds(tIds);
        exam.setLesson(lesson);
        exam.setAvailableRoom(availableRoom);

        try{
            if(examService.getT(exam)==null){
                examService.insert(exam);
                ajaxJson.setSuccess(true);}
            else {
                ajaxJson.setMsg("该考次已存在");
                ajaxJson.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }



    @ResponseBody
    @PostMapping("/validateExam")
    public AjaxJson validateExam(Integer availableRoomId){
        AjaxJson ajaxJson=new AjaxJson();
        Exam exam=new Exam();
        AvailableRoom availableRoom=new AvailableRoom();
        availableRoom.setId(availableRoomId);
        exam.setAvailableRoom(availableRoom);
        try{
            Exam exam1=examService.getT(exam);
            if(exam1!=null){
                ajaxJson.getBody().put("exam",exam1);
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
            Exam exam=examService.get(id);
            ajaxJson.getBody().put("exam",exam);
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
           examService.delete(id);
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
    @RequestMapping("/deleteExams")
    public AjaxJson deleteLessons(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();

        try{
            examService.deleteExams(id);
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
    @GetMapping("/generateExam")
    public AjaxJson generateExam(){

        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Exam> exams=examService.generateExam1();
            if(exams!=null&&exams.size()>0){
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("排考成功");
            }else{
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("排考失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("排考过程执行失败");
        }

        return ajaxJson;

    }
//
//    @ResponseBody
//    @GetMapping("/generateExam")
//    public List<Exam> generateExam1(){
//        return examService.generateExam1();
//    }

    @ResponseBody
    @GetMapping("/validateExisitExam")
    public AjaxJson validateExisitExam(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            boolean flag=examService.vailidateExisitExam();
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
        return examService.test();
    }

    @ResponseBody
    @GetMapping("/deleteAll")
    public AjaxJson deleteAll(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            examService.deleteAllExams();
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;

    }

    @ResponseBody
    @GetMapping("/findllz")
    public AjaxJson findllz(){
        AjaxJson ajaxJson=new AjaxJson();

        try{
            List<Exam> examList = examService_llz.generateExam();
            ajaxJson.getBody().put("exams",examList);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);

        }
        return ajaxJson;
    }

}
