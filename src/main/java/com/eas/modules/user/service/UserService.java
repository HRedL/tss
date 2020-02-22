package com.eas.modules.user.service;

import com.eas.common.persistence.CrudService;
import com.eas.modules.user.dao.UserDao;
import com.eas.modules.user.entity.User;
import com.eas.modules.user.service.ex.PasswordNotMatchException;
import com.eas.modules.user.service.ex.PasswordNotEqualException;
import com.eas.modules.user.service.ex.UserNotFoundException;
import com.eas.modules.user.service.ex.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户service
 * @Author mqy
 * @Version 2018/10/23
 */
@Service
public class UserService extends CrudService<UserDao,User> {

    @Autowired
    UserDao userDao;

    public Integer upadtePassword(
            Integer uid,
            String oldPassword,
            String newPassword,
            String confirmPassword) {
        // 根据uid查询用户数据
        User user = userDao.findUserById(uid);
        // 判断查询到的数据是否为null
        if(user == null){
            // 查询到的数据为null，即用户数据并不存在
            // 抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在，可能已经被删除！请联系管理员！");
        } else if(!newPassword.equals(confirmPassword)) {
            throw new PasswordNotEqualException("两次密码不一样！");
        } else {
            // 查询到匹配数据，即用户数据存在，则判断密码是否匹配
            if(user.getPassword().equals(oldPassword)){
                // 原密码匹配，则创建User对象
                User newUser = new User();
                // 将logname、uid和newPassword封装
                newUser.setId(uid);
                newUser.setPassword(newPassword);
                // 执行修改
                return userDao.update(newUser);
            } else {
                // 原密码不匹配，则抛出PasswordNotMatchException
                throw new PasswordNotMatchException("原密码不正确！");
            }
        }
    }

    public String judgeLoginType(String type){
        String url = "/user/login";
        if("1".equals(type)){
            url = "/views/sysmanager/sysManagerMain.jsp";
        } else if("4".equals(type)){
            url = "/views/manager/managerMain.jsp";
        } else if("3".equals(type)){
            url = "/views/teacher/teacherMain.jsp";
        } else if("2".equals(type)){
            url = "/views/student/studentMain.jsp";
        }
        return url;
    }

    public User login(String logname, String password, String type){
        if("sysmanager".equals(type)) {
            type = "1";
        } else if("manager".equals(type)) {
            type = "4";
        } else if("teacher".equals(type)) {
            type = "3";
        } else if("student".equals(type)) {
            type = "2";
        }
        //根据用户账号获取用户数据
        User u = userDao.findUserByLognameAndType(logname, type);
        //判断是否获取到用户数据
        if(u != null) {
            //数据不为null，表示获取到了数据，即用户账号存在
            //然后判断密码
            if(u.getPassword().equals(password)){
                //密码匹配，则登陆成功
                return u;
            }else{
                //密码不匹配，则登录失败
                throw new PasswordNotMatchException("密码错误");
            }
        } else {
            //数据为null，即根据用户账号查询不到有效数据，即用户账号不存在
            //抛出业务异常
            throw new UsernameNotFoundException("用户账号不存在");
        }
    }

    public Integer changeInfo(
            Integer uid,
            String logname,
            String oldPassword,
            String newPassword) {
        // 根据uid查询用户数据
        User user = userDao.findUserById(uid);
        // 判断查询到的数据是否为null
        if(user == null){
            // 查询到的数据为null，即用户数据并不存在
            // 抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在，可能已经被删除！请联系管理员！");
        } else {
            // 查询到匹配数据，即用户数据存在，则判断密码是否匹配
            if(user.getPassword().equals(oldPassword)){
                // 原密码匹配，则创建User对象
                User newUser = new User();
                // 将logname、uid和newPassword封装
                newUser.setLogname(logname);
                newUser.setId(uid);
                newUser.setPassword(newPassword);
                // 执行修改
                return userDao.update(newUser);
            } else {
                // 原密码不匹配，则抛出PasswordNotMatchException
                throw new PasswordNotMatchException("原密码不正确！");
            }
        }
    }

    public Integer changePersonage(
            Integer uid,
            String logname,
            String name,
            String pic
    ) {
        // 根据uid查询用户数据
        User user = userDao.findUserById(uid);
        // 判断查询到的数据是否为null
        if(user == null){
            // 查询到的数据为null，即用户数据并不存在
            // 抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在，可能已经被删除！请联系管理员！");
        } else {
            User newUser = new User();
            // 将logname、uid和name封装
            newUser.setLogname(logname);
            newUser.setId(uid);
            newUser.setName(name);
            newUser.setPic(pic);
            // 执行修改
            return userDao.update(newUser);
        }
    }

    @Override
    public User get(int id) {
        return super.get(id);
    }

    @Override
    public List<User> findAllList() {
        return super.findAllList();
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public int insert(User entity) {
        return super.insert(entity);
    }

    @Override
    public int update(User entity) {
        return super.update(entity);
    }


    public List<User> findUserByCondition(String queryText){
        return userDao.findUserByCondition(queryText);
    }
    public User findUserByLogname(String logname){return userDao.findUserByLogname(logname);}
    public void deleteUsers(Integer[] id) {
        userDao.deleteUsers(id);
    }
}
