package com.eas.modules.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eas.common.json.ResponseResult;
import com.eas.common.persistence.BaseController;
import com.eas.modules.student.entity.Student;
import com.eas.modules.student.service.StudentService;
import com.eas.modules.teacher.entity.Teacher;
import com.eas.modules.teacher.service.TeacherService;
import com.eas.modules.user.entity.User;
import com.eas.modules.user.service.UserService;
import com.eas.modules.user.service.ex.PasswordNotMatchException;
import com.eas.modules.user.service.ex.PasswordNotEqualException;
import com.eas.modules.user.service.ex.UserNotFoundException;
import com.eas.modules.user.service.ex.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.eas.common.json.AjaxJson;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * @Author mqy
 * @Version 2018/10/23
 */
@RequestMapping(value = "user")
@Controller
public class UserController extends BaseController{

    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;

    @RequestMapping(method=RequestMethod.POST, value="/handle_login")
    @ResponseBody
    public ResponseResult<Void> handleLogin(
            @RequestParam("logname") String logname,
            @RequestParam("password") String password,
            @RequestParam("type") String type,
            HttpSession session
    ) {
        //声明返回值
        //无论登录成功与否，都返回ResponseResult<Void>
        ResponseResult<Void> rr;
        try {
            System.out.println("do handleLogin");
            //调用userService完成登录
            User user = userService.login(logname, password, type);
            System.out.println("logname:" + user.getLogname());
            rr = new ResponseResult<Void>(1);
            //登录成功，调用Httpsession对象的setAttribute()方法
            session.setAttribute("user", user);
            session.setAttribute("uid", user.getId());
            System.out.println("session:" + session.getAttribute("user"));
            return rr;
        } catch (UsernameNotFoundException e) {
            //用户名不存在
            rr = new ResponseResult<Void>(0, e.getMessage());
            System.out.println("结果：" + e.getMessage());
            return rr;
        } catch (PasswordNotMatchException e) {
            //密码不正确
            rr = new ResponseResult<Void>(-1, e.getMessage());
            System.out.println("结果：" + e.getMessage());
            return rr;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "update_Pwd")
    @ResponseBody
    public ResponseResult<Void> updatePwd(
            @RequestParam("old-pwd") String oldPassword,
            @RequestParam("new-pwd") String newPassword,
            @RequestParam("confirm-pwd") String confirmPassword,
            HttpSession session
    ) {
        //声明ResponseResult对象
        ResponseResult<Void> rr;
        //从session中获取用户ID
        System.out.println(oldPassword + newPassword + confirmPassword);
        Integer uid = getUidFromSession(session);
        System.out.println(uid);
        try {
            //调用Service中的changeInfo()方法
            userService.upadtePassword(
                    uid, oldPassword, newPassword, confirmPassword);
            //修改用户信息成功
            rr = new ResponseResult<Void>(1, "修改用户信息成功！");
            return rr;
        } catch (UserNotFoundException e) {
            //修改用户信息失败：用户不存在
            rr = new ResponseResult<Void>(0, e.getMessage());
            return rr;
        } catch (PasswordNotMatchException e) {
            //修改用户信息失败：旧密码输入错误
            rr = new ResponseResult<Void>(-1, e.getMessage());
            return rr;
        } catch (PasswordNotEqualException e) {
            //两次密码不一致
            rr = new ResponseResult<Void>(-2, e.getMessage());
            return rr;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "handle_change_info")
    @ResponseBody
    public ResponseResult<Void> handleChangeInfo(
            @RequestParam("logname") String logname,
            @RequestParam("old-password") String oldPassword,
            @RequestParam("new-password") String newPassword,
            HttpSession session
    ) {
        //声明ResponseResult对象
        ResponseResult<Void> rr;
        //从session中获取用户ID
        Integer uid = getUidFromSession(session);
        System.out.println(uid);
        try {
            //调用Service中的changeInfo()方法
            userService.changeInfo(
                    uid, logname, oldPassword, newPassword);
            //修改用户信息成功
            rr = new ResponseResult<Void>(1, "修改用户信息成功！");
            return rr;
        } catch (UserNotFoundException e) {
            //修改用户信息失败：用户不存在
            rr = new ResponseResult<Void>(0, e.getMessage());
            return rr;
        } catch (PasswordNotMatchException e) {
            //修改用户信息失败：旧密码输入错误
            rr = new ResponseResult<Void>(-1, e.getMessage());
            return rr;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "handle_change_personage")
    @ResponseBody
    public ResponseResult<Void> handleChangePersonage(
            @RequestParam("pic") String pic,
            @RequestParam("logname") String logname,
            @RequestParam("name") String name,
            HttpSession session
    ) {
        //声明ResponseResult对象
        ResponseResult<Void> rr;
        //从session中获取用户ID
        Integer uid = getUidFromSession(session);
        System.out.println(uid);
        try {
            //调用Service中的changePersonage()方法
            userService.changePersonage(uid, logname, name, pic);
            //修改用户信息成功
            System.out.println("修改用户信息成功");
            rr = new ResponseResult<Void>(1, "修改成功！");
            System.out.println(rr);
            return rr;
        } catch (UserNotFoundException e) {
            //修改用户信息失败：用户不存在
            rr = new ResponseResult<Void>(0, e.getMessage());
            return rr;
        }
    }

    @RequestMapping("/login")
    public String showLogin(){
        return "login/login";
    }

    @RequestMapping("/main_page")
    public String toMainPage(HttpSession session){
        User user = (User)session.getAttribute("user");
        String type = user.getType();
        System.out.println("type:" + type);
        String url = userService.judgeLoginType(type);
        System.out.println("url:" + url);
        return "redirect:" + url;
    }



    @RequestMapping(value = "/get/{id}")
    public String getUser(Map<String,Object> map,@PathVariable("id") Integer id){
        User user=userService.get(id);
        map.put("user",user);
        return "user/lookUser";
    }

    @RequestMapping(value = "/list")
    public String getAllUsers(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Map<String,Object> map){
        //引入分页查询，使用PageHelper分页功能
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pn,5);
        List<User> users=userService.findAllList();
        //使用pageInfo包装查询后的结果，封装了详细的查询数据，其中参数5是页码导航连续显示的页数
        PageInfo pageInfo = new PageInfo(users,5);
        map.put("pageInfo",pageInfo);
        return "sysmanager/user/manageUser";
    }

    @RequestMapping("/getUserByCondition")
    public String queryLessonByCondition(@RequestParam(value = "queryText")String queryText,Map<String,Object> map){
        PageHelper.startPage(1,5);
        List<User> users=userService.findUserByCondition(queryText);

        PageInfo pageInfo=new PageInfo(users,5);
        map.put("pageInfo",pageInfo);
        return "sysmanager/user/manageUser";
    }

    @RequestMapping("/detail")
    public String toDetailPage(@RequestParam("id")Integer id,Map<String,Object> map){
        User user=userService.get(id);
        map.put("user",user);
        return "/sysmanager/user/detailUser";
    }
    @ResponseBody
    @RequestMapping(value = "/delete/{id}")
    public AjaxJson deleteUser(@PathVariable("id") Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            User user=userService.get(id);
            if(user.getType().equals("2")){
                Student student=new Student();
                student.setSnum(user.getLogname());
                Student student1=studentService.getT(student);
                studentService.delete(student1.getId());
            }
            else if(user.getType().equals("3")) {
                Teacher teacher=new Teacher();
                teacher.setTnum(user.getLogname());
                Teacher teacher1=teacherService.getT(teacher);
                teacherService.delete(teacher1.getId());
            }
            userService.delete(id);
            ajaxJson.setMsg("删除成功");
            ajaxJson.setSuccess(true);
        }
        catch (Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("删除失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @RequestMapping("/toAddPage")
    public String toAddPage() {
        return "sysmanager/user/addUser";
    }
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxJson add(User user) {
        AjaxJson ajaxJson=new AjaxJson();

        //try{
            if(userService.getT(user)==null){
                userService.insert(user);
                if(user.getType().equals("3")){
                    Teacher teacher=new Teacher();
                    teacher.setTnum(user.getLogname());
                    teacher.setTname(user.getName());
                    teacher.setSex("");
                    teacher.setAcademy("");
                    teacher.setDept("");
                    teacher.setTtel("");
                    teacher.setTINVTIMES(0);
                    teacherService.insert(teacher);/*加ajaxjson*/
                }
                else if(user.getType().equals("2")) {
                    Student student=new Student();
                    student.setSnum(user.getLogname());
                    student.setSname(user.getName());
                    student.setSex("");
                    student.setGid(0);
                    studentService.insert(student);
                }
                ajaxJson.setMsg("新增用户成功");
                ajaxJson.setSuccess(true);
            }
            else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("用户已存在");
            }
//       }catch (Exception e){
//           ajaxJson.setSuccess(false);
//            ajaxJson.setMsg("新增用户失败");
//        }
        return ajaxJson;
    }

    @RequestMapping(value = "/toUpdatePage",method = RequestMethod.GET)
    public String toUpdatePage(Model model, @RequestParam("id")Integer id){

        model.addAttribute("user",userService.get(id));
        return "/sysmanager/user/updateUser";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(User user){
        User user1=userService.findUserByLogname(user.getLogname());
        user.setId(user1.getId());
        userService.update(user);
        if(user.getType().equals("3")){
            Teacher teacher=new Teacher();
            teacher.setTnum(user.getLogname());
            teacher.setId(teacherService.getT(teacher).getId());
            teacher.setTname(user.getName());
            teacherService.update(teacher);
        }
        else if (user.getType().equals("2")){
            Student student=new Student();
            student.setSnum(user.getLogname());
            student.setSname(user.getName());
            student.setId(studentService.getT(student).getId());
            studentService.update(student);
        }
        return "redirect:/user/list";
    }
    @ResponseBody
    @RequestMapping(value = "/deleteUsers")
    public AjaxJson deleteUsers(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            for(int i=0;i<id.length;i++){
                User user=userService.get(id[i]);
                if(user.getType().equals("3")){
                    Teacher teacher=new Teacher();
                    teacher.setTnum(user.getLogname());
                    teacher.setTname(user.getName());
                    teacherService.delete(teacherService.getT(teacher).getId());
                }
                else if(user.getType().equals("2")){
                    Student student=new Student();
                    student.setSnum(user.getLogname());
                    student.setSname(user.getName());
                    studentService.delete(studentService.getT(student).getId());
                }
            }
            userService.deleteUsers(id);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("成功");
        }
        catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("删除失败");
        }
        return ajaxJson;
    }

}
