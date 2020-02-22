package com.eas.modules.teacher.controller;

import com.eas.modules.grade.entity.Grade;
import com.eas.modules.student.entity.Student;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.common.json.ResponseResult;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.teacher.entity.Teacher;
import com.eas.modules.teacher.service.TeacherService;
import com.eas.modules.teacher.service.ex.TeaExamInfNotFoundException;
import com.eas.modules.user.entity.User;
import com.eas.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author llp
 * @Version 2018/10/23
 */
@RequestMapping("teacher")
@Controller
public class TeacherController{

    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;


    @RequestMapping("/show_changePassword")
    public String showChangePassword(){
        return "teacher/changePassword";
    }

    @RequestMapping("/show_notice")
    public String showNotice(){
        return "teacher/notice";
    }

    @RequestMapping("/show_personage")
    public String showPersonage(HttpSession session, ModelMap modelMap){
        try {
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            String acaAndDept = teacherService.findAcaAndDeptByTnum(teacher.getTnum());
            String sex = teacherService.findSexByType(teacher);
            modelMap.addAttribute("teacher", teacher);
            modelMap.addAttribute("sex", sex);
            modelMap.addAttribute("acaAndDept", acaAndDept);
            return "teacher/personage";
        } catch (NullPointerException e) {
            modelMap.addAttribute(
                    "errorPersonage", "没有您的个人信息！");
            return "teacher/nullPersonage";
        }
    }

    @RequestMapping("/show_tea_info")
    public String showTeaInfo(HttpSession session, ModelMap modelMap){
        Teacher teacher = null;
        try {
            User user = (User) session.getAttribute("user");
            System.out.println("logname:" + user.getLogname());
            teacher = teacherService.findTeacherByTnum(user.getLogname());
            session.setAttribute("teacher", teacher);
            System.out.println(
                    "teacher:" + teacher.getTname() + " tnum:" + teacher.getTnum());
            List<Exam> exams = teacherService.findExamsByTid(teacher.getId());
            System.out.println("exams:" + exams + "长度：" + exams.size());
            System.out.println("监考科目：" + exams.get(0).getLesson().getSubject());
            modelMap.addAttribute("exams", exams);
            return "teacher/teacherInfo";
        } catch (NullPointerException e) {
            modelMap.addAttribute(
                    "errorInfo", "没有查到您的监考信息！");
            session.setAttribute("teacher", teacher);
            return "teacher/nullTeacherInfo";
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "show_tea_exam_inf")
    @ResponseBody
    public ResponseResult<List> getTeaExamInf(@RequestParam("tnum") String tnum){//ResponseResult<List>
        System.out.println("showTeaExamInf()");
        ResponseResult<List> rr;
        try {
            List<Exam> exam = teacherService.getTeaExamInfByLogname(tnum);
            rr = new ResponseResult<List>(1, exam);
            return rr;
        }catch (TeaExamInfNotFoundException e) {
            rr = new ResponseResult<List>(-2, e.getMessage());
            return rr;
        }
    }

//    @RequestMapping(value = "/get/{id}")
//    public ModelAndView getTeacher(@PathVariable("id")Integer id)
//    {
//        Teacher teacher=teacherService.get(id);
//        //分开指定视图与模型
//        //ModelAndView modelAndView=new ModelAndView();
//        //指定视图名称
//        //modelAndView.setViewName("/teacher/lookTeacher");
//        //添加模型中的对象
//        //modelAndView.getModel().put("teacher",teacher);
//        //return modelAndView;
//        return new ModelAndView("teacher/seeTeacher", "teacher", teacher);
//    }

//    @RequestMapping(value = "/list")
//    public String getAllTeachers(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Map<String,Object> map){
//        //引入分页查询，使用PageHelper分页功能
//        //在查询之前传入当前页，然后多少记录
//        PageHelper.startPage(pn,5);
//        List<Teacher> teachers=teacherService.findAllList();
//        //使用pageInfo包装查询后的结果，封装了详细的查询数据，其中参数5是页码导航连续显示的页数
//        PageInfo pageInfo = new PageInfo(teachers,5);
//        map.put("pageInfo",pageInfo);
//        return "manager/teacher/manageTeacher";
//    }

//    @RequestMapping("/getTeacherByCondition")
//    public String queryTeacherByCondition(@RequestParam(value = "queryText")String queryText,Map<String,Object> map){
//        PageHelper.startPage(1,5);
//        List<Teacher> teachers=teacherService.findTeacherByCondition(queryText);
//
//        PageInfo pageInfo=new PageInfo(teachers,5);
//        map.put("pageInfo",pageInfo);
//        return "manager/teacher/manageTeacher";
//    }

//    @RequestMapping(value = "/delete/{id}")
//    public String deleteTeacher(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
//        try{
//            teacherService.delete(id);
//            redirectAttributes.addAttribute("info","删除成功");
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            redirectAttributes.addAttribute("info","删除失败");
//        }
//        return "redirect:/teacher/list";
//    }


    @ResponseBody
    @GetMapping("/list")
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
            List<Teacher> teachers=teacherService.findListByText(queryText);

            PageInfo pageInfo=new PageInfo(teachers,5);
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
    @PostMapping("/delete")
    public AjaxJson delete(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{String logname=teacherService.get(id).getTnum();
            Integer uid=userService.findUserByLogname(logname).getId();
            userService.delete(uid);
            teacherService.delete(id);
            ajaxJson.setMsg("删除成功");
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            ajaxJson.setMsg("删除失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;

    }


    @ResponseBody
    @PostMapping(value = "/update")
    public AjaxJson update(Teacher teacher) {
        AjaxJson ajaxJson = new AjaxJson();
        User user=userService.findUserByLogname(teacher.getTnum());
        try {
            user.setLogname(teacher.getTnum());
            user.setName(teacher.getTname());
            userService.update(user);
            teacherService.update(teacher);
            ajaxJson.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("修改教师失败");
        }
        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/add")
    public AjaxJson add(Teacher teacher){
        AjaxJson ajaxJson=new AjaxJson();
        User user=new User();
        try{
            if(teacherService.getT(teacher)==null){
                teacherService.insert(teacher);
                user.setLogname(teacher.getTnum());
                user.setName(teacher.getTname());
                user.setPassword("123456");
                user.setPic("1.jpg");
                user.setExist_pic(0);
                user.setType("3");
                userService.insert(user);
                ajaxJson.setSuccess(true);}
            else {
                ajaxJson.setMsg("该教师已存在");
                ajaxJson.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加教师失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/initUpdateTeacher")
    public AjaxJson initUpdateTeacher(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Teacher teacher=teacherService.get(id);
            ajaxJson.getBody().put("teacher",teacher);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }



    @ResponseBody
    @RequestMapping("/deleteTeachers")
    public AjaxJson deleteTeachers(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();
        String[]lognames=new String[id.length];
        Integer[]uids=new Integer[id.length];
        try{
            for(int i=0;i<id.length;i++){
                lognames[i]=teacherService.get(id[i]).getTnum();
                uids[i]=userService.findUserByLogname(lognames[i]).getId();
            }
            userService.deleteUsers(uids);
            teacherService.deleteTeachers(id);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("删除失败");
        }

        return ajaxJson;
    }

//    @RequestMapping("/toAddPage")
//    public String toAddPage() {
//        return "manager/teacher/addTeacher";
//    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String add(Teacher teacher) {
//        teacherService.insert(teacher);
//        return "redirect:/teacher/list";
//    }

//    @RequestMapping("/detail")
//    public String toDetailPage(@RequestParam("id")Integer id,Map<String,Object> map){
//        Teacher teacher=teacherService.get(id);
//        map.put("teacher",teacher);
//        return "/manager/teacher/detailTeacher";
//    }

//    @RequestMapping(value = "/toUpdatePage",method = RequestMethod.GET)
//    public String toUpdatePage(Model model, @RequestParam("id")Integer id){
//
//        model.addAttribute("teacher",teacherService.get(id));
//        return "/manager/teacher/updateTeacher";
//    }

//    @RequestMapping(value="/update",method = RequestMethod.POST)
//    public String update(Teacher teacher){
//        teacherService.update(teacher);
//
//        return "redirect:/teacher/list";
//    }


    @ResponseBody
    @GetMapping("/getAllTeachers")
    public AjaxJson getAllTeachers(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Teacher> teachers=teacherService.findAllList();
            ajaxJson.getBody().put("teachers",teachers);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }

        return ajaxJson;
    }



}


