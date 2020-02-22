package com.eas.modules.period.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.modules.period.entity.Period;
import com.eas.modules.period.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lenovo
 * @Version 2018/10/23
 */
@Controller
@RequestMapping(value = "period")
public class PeriodController extends BaseController{

    @Autowired
    PeriodService periodService;


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
            List<Period> periods = periodService.findListByText(queryText);

            PageInfo pageInfo=new PageInfo(periods,5);
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
        try{
            periodService.delete(id);
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
    public AjaxJson update(Period period) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            periodService.update(period);
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
    public AjaxJson add(Period period){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            if(periodService.getT(period)==null) {
                periodService.insert(period);
                ajaxJson.setSuccess(true);
            }
            else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该时间已存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加教师失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/initUpdateTime")
    public AjaxJson initUpdateTime(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Period period = periodService.get(id);
            ajaxJson.getBody().put("period", period);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }



    @ResponseBody
    @RequestMapping("/deleteTimes")
    public AjaxJson deleteTeachers(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();

        try{
            periodService.deleteTimes(id);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("成功");
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("删除失败");
        }

        return ajaxJson;
    }






//    @RequestMapping("/list")
//    public String listAllTeachers(@RequestParam(value = "pageNumber",defaultValue = "1")Integer pageNumber, Map<String,Object> map){
//        PageHelper.startPage(pageNumber,5);
//        List<Period> times=timeService.findAllList();
//
//        PageInfo pageInfo=new PageInfo(times,5);
//        map.put("pageInfo",pageInfo);
//        return "manager/period/manageTime";
//    }
//
//    @RequestMapping("toAddPage")
//    public String toAddPage(){
//        return "manager/period/addTime";
//    }
//
//    @RequestMapping("/get/{id}")
//    public String getPeriod(Map<String,Object>map,@PathVariable("id") Integer id){
//        Period period=timeService.get(id);
//        map.put("period",period);
//        return "period/Period";
//    }
//    @RequestMapping("/delete/{id}")
//    public String deleteTime(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
//        try{
//            timeService.delete(id);/*delete方法删除数据*/
//            redirectAttributes.addAttribute("info","删除成功");/*信息为“删除成功”或“删除失败”，在jsp页面上使用${param.info}引用*/
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            redirectAttributes.addAttribute("info", "删除失败");
//        }
//        return "redirect:/period/list";/*重定向*/
//    }
//    @RequestMapping(value = "/add",method = RequestMethod.POST)
//    public String add(Period period){
//        timeService.insert(period);
//        return "redirect:/period/list";
//
//    }
//    @RequestMapping(value = "/toAddpage")
//    public String toAddpage(){
//        return"period/addTime";}
//
//    @RequestMapping(value ="/toUpdate/{id}")
//    public String toupdate(Model model, @PathVariable("id") Integer id){
//        model.addAttribute("period",timeService.get(id));
//        return"period/updateTime";
//    }
//    @RequestMapping(value = "/update",method = RequestMethod.POST)
//    public  String update(Period period){
//        System.out.println(period);
//        timeService.update(period);
//        return "redirect:/period/list";
//    }


    @ResponseBody
    @GetMapping("/getAllTimes")
    public AjaxJson getAllTimes(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Period> periods = periodService.findAllList();
            ajaxJson.getBody().put("periods", periods);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

}
