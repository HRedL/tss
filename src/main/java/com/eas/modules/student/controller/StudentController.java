package com.eas.modules.student.controller;

import com.eas.common.json.AjaxJson;
import com.eas.common.json.ResponseResult;
import com.eas.modules.exam.entity.Exam;
import com.eas.modules.grade.entity.Grade;
import com.eas.modules.student.entity.Student;
import com.eas.modules.student.service.StudentService;
import com.eas.modules.student.service.ex.StuExamInfNotFoundException;
import com.eas.modules.user.entity.User;
import com.eas.modules.user.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author llp
 * @Version 2018/10/22
 */
@RequestMapping("student")
@Controller
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;

    @RequestMapping("/show_changePassword")
    public String showChangePassword(){
        return "student/changePassword";
    }

    @RequestMapping("/show_notice")
    public String showNotice(){
        return "student/notice";
    }

    @RequestMapping("/show_personage")
    public String showPersonage(HttpSession session, ModelMap modelMap){
        try {
            Student student = (Student)session.getAttribute("student");
            String acaAndDept = studentService.findAcaAndDeptByGid(student.getGid());
            String sex = studentService.findSexByType(student);
            Grade grade = studentService.findGradeByGid(student.getGid());
            modelMap.addAttribute("student", student);
            modelMap.addAttribute("grade", grade);
            modelMap.addAttribute("sex", sex);
            modelMap.addAttribute("acaAndDept", acaAndDept);
            return "student/personage";
        } catch (NullPointerException e) {
            modelMap.addAttribute(
                    "errorPersonage", "没有您的个人信息！");
            return "student/nullPersonage";
        }
    }

    @RequestMapping("/show_stu_info")
    public String showStuInfo(HttpSession session, ModelMap modelMap){
        Student student = null;
        try {
            User user = (User) session.getAttribute("user");
            System.out.println("logname:" + user.getLogname());
            student = studentService.findStudentBySnum(user.getLogname());
            session.setAttribute("student", student);
            System.out.println(
                    "student:" + student.getSname() + " gid:" + student.getGid());
            List<Exam> exams = studentService.findExamsByGid(student.getGid());
            System.out.println("exams:" + exams + "长度：" + exams.size());
            System.out.println("考试科目：" + exams.get(0).getLesson().getSubject());
            modelMap.addAttribute("exams", exams);
            return "student/studentInfo";
        } catch (NullPointerException e) {
            modelMap.addAttribute(
                    "errorInfo", "没有查到您的考试信息！");
            session.setAttribute("student", student);
            return "student/nullStudentInfo";
        }
    }

    @RequestMapping("/student_info")
    public String toUserInfo() {
        return "redirect:/views/student/studentInfo.jsp";
    }

    @RequestMapping(method = RequestMethod.GET, value = "show_stu_exam_inf")
    @ResponseBody
    public ResponseResult<List> getStuExamInf(
            @RequestParam("snum") String snum
    ){
        System.out.println("showStuExamInf()");
        ResponseResult<List> rr;
        try {
            List<Exam> exam = studentService.getStuExamInfByLogname(snum);
            rr = new ResponseResult<List>(1, exam);
            return rr;
        }catch (StuExamInfNotFoundException e) {
            rr = new ResponseResult<List>(-2, e.getMessage());
            return rr;
        }
    }

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
            List<Student> students=studentService.findListByText(queryText);

            PageInfo pageInfo=new PageInfo(students,5);
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
        try{String logname=studentService.get(id).getSnum();
            Integer uid=userService.findUserByLogname(logname).getId();
            userService.delete(uid);
            studentService.delete(id);
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
    public AjaxJson update(Student student) {
        AjaxJson ajaxJson = new AjaxJson();
        User user=userService.findUserByLogname(student.getSnum());
        try {
            user.setLogname(student.getSnum());
            user.setName(student.getSname());
            userService.update(user);
            studentService.update(student);
            ajaxJson.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("修改学生失败");
        }
        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/add")
    public AjaxJson add(Student student){
        AjaxJson ajaxJson=new AjaxJson();
        User user=new User();
        try{
            if(studentService.getT(student)==null){
                studentService.insert(student);
                user.setLogname(student.getSnum());
                user.setPassword("123456");
                user.setName(student.getSname());
                user.setPic("1.jpg");
                user.setExist_pic(0);
                user.setType("2");
                userService.insert(user);
                ajaxJson.setSuccess(true);}
            else {
                ajaxJson.setMsg("该学生已存在");
                ajaxJson.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加学生失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/initUpdateStudent")
    public AjaxJson initUpdateStudent(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Student student=studentService.get(id);
            ajaxJson.getBody().put("student",student);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }



    @ResponseBody
    @RequestMapping("/deleteStudents")
    public AjaxJson deleteStudents(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();
        String[]lognames=new String[id.length];
        Integer[]uids=new Integer[id.length];
        try{
            for(int i=0;i<id.length;i++){
                lognames[i]=studentService.get(id[i]).getSnum();
                uids[i]=userService.findUserByLogname(lognames[i]).getId();
            }
            userService.deleteUsers(uids);
            studentService.deleteStudents(id);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("删除失败");
        }

        return ajaxJson;
    }

//    @RequestMapping(value = "/list")
//    public String getAllStudents(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Map<String,Object> map){
//        //引入分页查询，使用PageHelper分页功能
//        //在查询之前传入当前页，然后多少记录
//        PageHelper.startPage(pn,5);
//        List<Student> students=studentService.findAllList();
//        //使用pageInfo包装查询后的结果，封装了详细的查询数据，其中参数5是页码导航连续显示的页数
//        PageInfo pageInfo = new PageInfo(students,5);
//        map.put("pageInfo",pageInfo);
//        return "manager/student/manageStudent";
//    }

//    @RequestMapping("/getStudentByCondition")
//    public String queryStudentByCondition(@RequestParam(value = "queryText")String queryText,Map<String,Object> map){
//        PageHelper.startPage(1,5);
//        List<Student> students=studentService.findStudentByCondition(queryText);
//
//        PageInfo pageInfo=new PageInfo(students,5);
//        map.put("pageInfo",pageInfo);
//        return "manager/student/manageStudent";
//    }

//    @RequestMapping(value = "/delete/{id}")
//    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
//        try {
//            studentService.delete(id);
//            redirectAttributes.addAttribute("info", "删除成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            redirectAttributes.addAttribute("info", "删除失败");
//        }
//        return "redirect:/student/list";
//    }

//    @RequestMapping("/toAddPage")
//    public String toAddPage() {
//        return "manager/student/addStudent";
//    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String add(Student student) {
//        studentService.insert(student);
//        return "redirect:/student/list";
//    }
//
//    @RequestMapping("/detail")
//    public String toDetailPage(@RequestParam("id")Integer id,Map<String,Object> map){
//        Student student=studentService.get(id);
//        map.put("student",student);
//        return "/manager/student/detailStudent";
//    }

//    @RequestMapping(value = "/toUpdatePage",method = RequestMethod.GET)
//    public String toUpdatePage(Model model, @RequestParam("id")Integer id){
//
//        model.addAttribute("student",studentService.get(id));
//        return "/manager/student/updateStudent";
//    }
//
//    @RequestMapping(value = "/get/{id}")
//    public ModelAndView getStudent(@PathVariable("id") Integer id) {
//        Student student = studentService.get(id);
//        return new ModelAndView("student/seeStudent", "student", student);
//    }
//
//    @RequestMapping(value = "update",method = RequestMethod.POST)
//    public String update(Student student){
//        studentService.update(student);
//        return "redirect:/student/list";
//    }
}
