package com.eas.modules.task.controller;


import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.modules.task.entity.Task;
import com.eas.modules.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("task")
@Controller
public class TaskController extends BaseController {

    @Autowired
    TaskService taskService;

    @ResponseBody
    @GetMapping("/get")
    public AjaxJson get(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Task task= taskService.get(id);
            ajaxJson.getBody().put("task",task);
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
    public AjaxJson getT(Task task){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Task task1= taskService.getT(task);
            ajaxJson.getBody().put("task",task1);
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
    @GetMapping("/findAllList")
    public AjaxJson findAllList(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Task> tasks= taskService.findAllList();

            ajaxJson.getBody().put("tasks",tasks);
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
            taskService.delete(id);
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
    public AjaxJson update(Task task) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            taskService.update(task);
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
    public AjaxJson add(Task task){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            taskService.insert(task);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加失败");
        }

        return ajaxJson;
    }

}
