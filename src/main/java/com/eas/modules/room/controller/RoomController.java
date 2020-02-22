package com.eas.modules.room.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.modules.room.entity.Room;
import com.eas.modules.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author hwq
 * @Version 2018/10/23
 */
@Controller
@RequestMapping(value = "room")
public class RoomController extends BaseController {
    @Autowired
    RoomService roomService;





    @ResponseBody
    @GetMapping("/getRoomsByBid")
    public AjaxJson getRoomsByBid(Integer bid){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<Room> rooms=roomService.getRoomsByBid(bid);
            ajaxJson.setSuccess(true);
            ajaxJson.getBody().put("rooms",rooms);

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
            List<Room> rooms=roomService.findListByText(queryText);

            PageInfo pageInfo=new PageInfo(rooms,5);
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

//    @RequestMapping(value = "/delete/{id}")
//    public String deleteRoom(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
//        try {
//            roomService.delete(id);/*delete方法删除数据*/
//            redirectAttributes.addAttribute("info", "删除成功");/*信息为“删除成功”或“删除失败”，在jsp页面上使用${param.info}引用*/
//        } catch (Exception e) {
//            e.printStackTrace();
//            redirectAttributes.addAttribute("info", "删除失败");
//        }
//        return "redirect:/room/list";/*重定向*/
//    }

    @ResponseBody
    @PostMapping(value = "/delete")
    public AjaxJson delete(Integer id){

        AjaxJson ajaxJson=new AjaxJson();
        try{
            roomService.delete(id);
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
    @PostMapping(value = "/add")
    public AjaxJson add(Room room){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            if(roomService.getT(room)==null){
                roomService.insert(room);
                ajaxJson.setSuccess(true);}
            else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该教室已存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("添加教室失败");
        }
        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public AjaxJson update(Room room){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            roomService.update(room);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("修改教室失败");
        }

        return ajaxJson;
    }

    @ResponseBody
    @PostMapping(value = "/deleteRooms")
    public AjaxJson deleteRooms(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();

        try{
            roomService.deleteRooms(id);
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
    @GetMapping("/getAllRooms")
    public AjaxJson getAllRooms(){
        AjaxJson ajaxJson=new AjaxJson() ;
        try{
            List<Room> rooms=roomService.findListByText(null);
            ajaxJson.getBody().put("rooms",rooms);

            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/initUpdateRoom")
    public AjaxJson initUpdateLesson(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Room room=roomService.getWithoutDict(id);
            ajaxJson.getBody().put("room",room);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }



}
