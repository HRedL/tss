package com.eas.modules.lesson.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.common.utils.ExamUtils;
import com.eas.modules.grade.service.GradeServiceImpl;
import com.eas.modules.lesson.entity.Lesson;
import com.eas.modules.lesson.service.LessonService;
import com.eas.modules.teacher.entity.Teacher;
import com.eas.modules.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * @Author hhl
 * @Version 2018/10/20
 */
@Controller
@RequestMapping("lesson")
public class LessonController extends BaseController {

    @Autowired
    LessonService lessonService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    GradeServiceImpl gradeService;


//    @ResponseBody
//    @GetMapping("/initAddLesson")
//    public AjaxJson initAddLesson(){
//        AjaxJson ajaxJson=new AjaxJson();
//        try{
//            List<Teacher> teachers= teacherService.findAllList();
//            List<Grade> grades=gradeService.findAllGrade();
//
//            ajaxJson.getBody().put("teachers",teachers);
//            ajaxJson.getBody().put("grades",grades);
//            ajaxJson.setSuccess(true);
//        }catch (Exception e){
//            e.printStackTrace();
//            ajaxJson.setSuccess(false);
//        }
//        return ajaxJson;
//    }


    @ResponseBody
    @GetMapping("/initUpdateLesson")
    public AjaxJson initUpdateLesson(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Lesson lesson=lessonService.getWithoutDict(id);
            ajaxJson.getBody().put("lesson",lesson);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }



    @ResponseBody
    @RequestMapping("/list")
    public AjaxJson list(Integer pageNumber,Integer pageSize,String queryText){
        if(pageNumber==null){
            pageNumber=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        AjaxJson ajaxJson=new AjaxJson();
        try{
            PageHelper.startPage(pageNumber,pageSize);
            List<Lesson> lessons=lessonService.findListByText(queryText);

            PageInfo pageInfo=new PageInfo(lessons,5);
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

//    @RequestMapping(value="/getLessonByCondition",method = RequestMethod.POST)
//    public String queryLessonByCondition(@RequestParam(value = "queryText")String queryText,Map<String,Object> map){
//        PageHelper.startPage(1,20);
//        List<Lesson> lessons=lessonService.findLessonsByCondition(queryText);
//
//        PageInfo pageInfo=new PageInfo(lessons,1);
//        map.put("pageInfo",pageInfo);
//        return "manager/lesson/manageLesson";
//    }

    @ResponseBody
    @PostMapping(value = "/validateLesson")
    public AjaxJson add(String lnum) {
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Lesson lesson=lessonService.getLessonByLnum(lnum);
            if(lesson!=null){
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("已经存在该课程");
            }else{
                ajaxJson.setSuccess(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加过程中出现错误");
        }
        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/add")
    public AjaxJson add(Lesson lesson,Integer tid,String gids){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            if(lessonService.getT(lesson)==null){
                Teacher teacher= new Teacher();
                teacher.setId(tid);
                lesson.setTeacher(teacher);
                lesson.setgIds(gids);
                lessonService.insert(lesson);
                ajaxJson.setSuccess(true);}
            else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该课程已存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加课程失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/detail")
    public AjaxJson toDetailPage(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Lesson lesson=lessonService.get(id);
            ajaxJson.setSuccess(true);
            ajaxJson.getBody().put("lesson",lesson);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public AjaxJson update(Lesson lesson,Integer tid,String gids) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            Teacher teacher = new Teacher();
            teacher.setId(tid);
            lesson.setTeacher(teacher);
            lesson.setgIds(gids);
            lessonService.update(lesson);
            ajaxJson.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加课程失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public AjaxJson delete(Integer id){

        AjaxJson ajaxJson=new AjaxJson();
        try{
            lessonService.delete(id);
            ajaxJson.setMsg("删除成功");
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            logger.info(e.getMessage());
            ajaxJson.setMsg("删除失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;

    }



    @ResponseBody
    @RequestMapping("/deleteLessons")
    public AjaxJson deleteLessons(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();

        try{
            lessonService.deleteLessons(id);
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
    @GetMapping("/getAllLessons")
    public AjaxJson getAllLessons(){
        AjaxJson ajaxJson=new AjaxJson();

        try{
            List<Lesson> lessons=lessonService.findAllList();
            ajaxJson.getBody().put("lessons",lessons);
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
            List<List<Lesson>> lessonLists = ExamUtils.getLessonLists();
            ajaxJson.getBody().put("lessons",lessonLists.size());
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);

        }
        return ajaxJson;
    }

}
