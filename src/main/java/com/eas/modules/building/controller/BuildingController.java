package com.eas.modules.building.controller;

import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.modules.building.entity.Building;
import com.eas.modules.building.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @Author hwq
 * @Version 2018/10/22
 */
@Controller
@RequestMapping(value = "building")
public class BuildingController extends BaseController {
    @Autowired
    BuildingService buildingService;

    //根据campus查出building
    @ResponseBody
    @GetMapping("/getBuildingByCampus")
    public AjaxJson getBuildingByCampus(String campus){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Building> buildings=buildingService.getBuildingByCampus(campus);
            ajaxJson.getBody().put("buildings",buildings);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


    @RequestMapping("/get/{id}")
    public String getBuilding(Map<String, Object> map, @PathVariable("id") Integer id) {/*获取id*/
        Building building = buildingService.get(id);/*根据id用get方法获取building实体*/
        map.put("building", building);/*把building放入"building"中，在jsp页面中使用${building.xx}获取相应属性值*/
        return "building/showBuilding";
    }

    @RequestMapping(value = "/list")
    public String getAllBuilding(Map<String, Object> map) {
        List<Building> buildings = buildingService.findAllList();/*findAllList方法获取数据，放入buildings中*/
        map.put("buildings", buildings);
        return "building/showBuildings";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteBuilding(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            buildingService.delete(id);/*delete方法删除数据*/
            redirectAttributes.addAttribute("info", "删除成功");/*信息为“删除成功”或“删除失败”，在jsp页面上使用${param.info}引用*/
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("info", "删除失败");
        }
        return "redirect:/building/list";/*重定向*/
    }

    @RequestMapping("/toAddPage")
    public String toAddPage() {
        return "building/addBuilding";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Building building) {
        buildingService.insert(building);
        return "redirect:/building/list";
    }

    @RequestMapping(value = "/toUpdatePage/{id}", method = RequestMethod.GET)
    public String toUpdateBuilding(@PathVariable("id") Integer id, Map<String, Object> map) {
        Building building = buildingService.get(id);
        map.put("building", building);
        return "building/updateBuilding";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Building building) {
        System.out.println(building);
        buildingService.update(building);
        return "redirect:/building/list";
    }
}
