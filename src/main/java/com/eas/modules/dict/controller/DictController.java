package com.eas.modules.dict.controller;

import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.modules.dict.entity.Dict;
import com.eas.modules.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @Author mqy
 * @Version 2018/11/6
 */
@Controller
@RequestMapping(value = "dict")
public class DictController extends BaseController {

    @Autowired
    DictService dictService;

//    @ResponseBody
//    @GetMapping("/getDictByCondition")
//    public AjaxJson getDictByCondition(String type){
//        AjaxJson ajaxJson=new AjaxJson();
//        try{
//            Dict dict=new Dict();
//            dict.setType(type);
//            List<Dict> dicts= dictService.getDictsByCondition(type);
//            ajaxJson.setSuccess(true);
//            ajaxJson.getBody().put("dicts",dicts);
//        }catch (Exception e){
//            ajaxJson.setSuccess(false);
//        }
//        return ajaxJson;
//    }

    @RequestMapping(value = "/get/{id}")
    public String getDict(Map<String ,Object> map,@PathVariable("id") Integer id){/*获取id*/
        Dict dict=dictService.get(id);/*根据id用get方法获取dict实体*/
        map.put("dict",dict);/*把dict放入"dict"中，在jsp页面中使用${dict.xx}获取相应属性值*/
        return "dict/lookDict";
    }

    @RequestMapping(value = "/list")
    public String getAllDict(Map<String,Object> map){
        List<Dict> dicts=dictService.findAllList();/*findAllList方法获取数据，放入dicts中*/
        map.put("dicts",dicts);
        return "dict/lookDicts";
    }


    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try{
            dictService.delete(id);/*delete方法删除数据*/
            redirectAttributes.addAttribute("info","删除成功");/*信息为“删除成功”或“删除失败”，在jsp页面上使用${param.info}引用*/
        }
        catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("info", "删除失败");
        }
        return "redirect:/dict/list";/*重定向*/
    }

    @RequestMapping("/toAddPage")
    public String toAddPage() {
        return "dict/addDict";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Dict dict) {
        dictService.insert(dict);
        return "redirect:/dict/list";
    }

    @RequestMapping(value = "/toUpdatePage/{id}", method = RequestMethod.GET)
    public String toUpdateDict(@PathVariable("id") Integer id,Map<String,Object> map) {
        Dict dict=dictService.get(id);
        map.put("dict",dict);
        return "dict/updateDict";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String update(Dict dict){
        System.out.println(dict);
        dictService.update(dict);
        return "redirect:/dict/list";
    }




    @ResponseBody
    @GetMapping("/getDictsByType")
    public AjaxJson getDictsByType (String type){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Dict> dicts=dictService.getDictsByType(type);
            ajaxJson.getBody().put("dicts",dicts);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("查询失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @PostMapping("/getDictsByTypeAndParentId")
    public AjaxJson getDictsByTypeAndParentId (String type,String ptype,String pid){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Dict> dicts=dictService.getDictsByTypeAndParentId(type,ptype,pid);
            ajaxJson.getBody().put("dicts",dicts);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("查询失败");
        }

        return ajaxJson;
    }



}

