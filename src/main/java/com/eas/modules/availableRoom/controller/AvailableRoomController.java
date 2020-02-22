package com.eas.modules.availableRoom.controller;

import com.github.pagehelper.PageInfo;
import com.eas.common.json.AjaxJson;
import com.eas.common.persistence.BaseController;
import com.eas.common.utils.ExamUtils;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.availableRoom.service.AvailableRoomService;
import com.eas.modules.building.entity.Building;
import com.eas.modules.room.entity.Room;
import com.eas.modules.room.service.RoomService;
import com.eas.modules.period.entity.Period;
import com.eas.modules.period.service.PeriodService;
import com.eas.modules.week.entity.Week;
import com.eas.modules.week.service.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2018/11/12
 */
@RequestMapping(value="availableRoom")
@Controller
public class AvailableRoomController extends BaseController{
    @Autowired
    AvailableRoomService availableRoomService;

    @Autowired
    RoomService roomService;

    @Autowired
    PeriodService periodService;

    @Autowired
    WeekService weekService;

    @ResponseBody
    @RequestMapping("/list")
    public AjaxJson listAllAvailableRooms(Integer pageNumber,Integer pageSize,String queryText,String queryType){
        if(pageNumber==null){
            pageNumber=1;
        }
        if(pageSize==null){
            pageSize=20;
        }
        AjaxJson ajaxJson=new AjaxJson();
        try{

            Map<String ,Object> map=new HashMap();
            map.put("start",(pageNumber-1)*pageSize);
            map.put("pageSize",pageSize);
            map.put("queryText",queryText);
            map.put("queryType",queryType);
            //就是这一块不太行啊
            List<AvailableRoom> dbAvaiRooms=availableRoomService.findAvailableRoomsByCondition(map);



            PageInfo pageInfo=new PageInfo();
            //总页码
            int totalNumber=0;
            //当前的数据条数
            int totalSize=availableRoomService.getCount(map);
            //如果正好整除，总页码=总记录/每页记录数,不正好，那么，总页码=总记录/每页记录数+1
            if(totalSize%pageSize==0){
                totalNumber=totalSize/pageSize;
            }else {
                totalNumber=totalSize/pageSize+1;
            }
            //设置是否有前一页或后一页
            if(pageNumber==1){
                pageInfo.setHasPreviousPage(false);
            }else{
                pageInfo.setHasPreviousPage(true);
            }
            if(pageNumber==totalNumber){
                pageInfo.setHasNextPage(false);
            }else{
                pageInfo.setHasNextPage(true);
            }

            //填充页面分页条的数字
            if(totalNumber==1){
                pageInfo.setNavigatepageNums(new int[]{1});
            }else if(totalNumber==2){
                pageInfo.setNavigatepageNums(new int[]{1,2});
            }else if(totalNumber==3){
                pageInfo.setNavigatepageNums(new int[]{1,2,3});
            }else if(totalNumber==4){
                pageInfo.setNavigatepageNums(new int[]{1,2,3,4});
            }else if(pageNumber<=3){
                pageInfo.setNavigatepageNums(new int[]{1,2,3,4,5});
            }else if(pageNumber+3>totalNumber){
                int[] array=new int[5];
                array[0]=totalNumber-4;
                array[1]=totalNumber-3;
                array[2]=totalNumber-2;
                array[3]=totalNumber-1;
                array[4]=totalNumber;
                pageInfo.setNavigatepageNums(array);
            }else{
                int[] array=new int[5];
                array[0]=pageNumber-2;
                array[1]=pageNumber-1;
                array[2]=pageNumber;
                array[3]=pageNumber+1;
                array[4]=pageNumber+2;
                pageInfo.setNavigatepageNums(array);
            }
            //填充当夜所用数据
//            List<AvailableRoom> availableRooms=new ArrayList<>();
//            int i=0;
//            for(AvailableRoom availableRoom:dbAvaiRooms){
//                if(i/5+1==pageNumber){
//                    availableRooms.add(availableRoom);
//                }
//                i++;
//            }
            pageInfo.setList(dbAvaiRooms);
            //设置页面中显示多少条数据
            pageInfo.setPageSize(pageSize);

            pageInfo.setPages(totalNumber);
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



    //初始化添加页面
//    @ResponseBody
//    @GetMapping("/initAddPage")
//    public AjaxJson initAddPage(){
//        AjaxJson ajaxJson=new AjaxJson();
//        List<Week> weeks=weekService.findAllWeek();
//        List<Period> times=timeService.findAllList();
//        if(weeks.size()>0){
//            ajaxJson.getBody().put("weeks",weeks);
//        }
//        if(times.size()>0){
//            ajaxJson.getBody().put("times",times);
//        }
//        return ajaxJson;
//    }

    //初始化添加页面
    @ResponseBody
    @PostMapping("/initUpdatePage")
    public AjaxJson initUpdatePage(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            AvailableRoom availableRoom=availableRoomService.get1(id);
            ajaxJson.getBody().put("availableRoom",availableRoom);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }


        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/getAvailableRoomByRid")
    public AjaxJson getAvailableRoomByRnum(Integer rid){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<AvailableRoom> availableRooms=availableRoomService.getAvailableRoomByRid(rid);



            List<Week> weeks=weekService.findAllList();
            List<Period> periods = periodService.findAllList();
            ajaxJson.getBody().put("weeks",weeks);
            ajaxJson.getBody().put("periods", periods);
            ajaxJson.getBody().put("availableRooms",availableRooms);



            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }


        return ajaxJson;
    }



    @ResponseBody
    @PostMapping("/validateAvailableRoom")
    public AjaxJson validateAvailableRoom(Integer weekid,Integer timeid,Integer rid){
        AjaxJson ajaxJson=new AjaxJson();
        AvailableRoom availableRoom=new AvailableRoom();
        Week week=new Week();
        Period period =new Period();
        Room room=new Room();
        week.setId(weekid);
        period.setId(timeid);
        room.setId(rid);
        availableRoom.setWeek(week);
        availableRoom.setPeriod(period);
        availableRoom.setRoom(room);
        try{
            System.out.println(weekid+"****"+timeid+"****"+rid);
            //AvailableRoom availableRoom1=availableRoomService.getT(availableRoom);
            if(availableRoomService.getT(availableRoom)!=null){
                //ajaxJson.getBody().put("availableRoom",availableRoom1);
                ajaxJson.setMsg("已存在该可用教室");
                ajaxJson.setSuccess(false);
            }else{
                ajaxJson.setMsg("验证成功");
                ajaxJson.setSuccess(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("验证失败");
        }
        return ajaxJson;
    }


    @ResponseBody
    @PostMapping("/addAvailableRoom")
    public AjaxJson addAvailableRoom(Integer weekid,Integer timeid,Integer rid){
        AjaxJson ajaxJson=new AjaxJson();
        AvailableRoom availableRoom=new AvailableRoom();
        Week week=new Week();
        Period period=new Period();
        Room room=new Room();
        week.setId(weekid);
        period.setId(timeid);
        room.setId(rid);
        availableRoom.setWeek(week);
        availableRoom.setPeriod(period);
        availableRoom.setRoom(room);
        try{
            if(availableRoomService.getT(availableRoom)==null){
                availableRoomService.insert(availableRoom);
                ajaxJson.setSuccess(true);}
            else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("该可用教室已存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @PostMapping("/updateAvailableRoom")
    public AjaxJson updateAvailableRoom(Integer id,Integer weekid,Integer timeid,Integer rid){
        AjaxJson ajaxJson=new AjaxJson();
        AvailableRoom availableRoom=new AvailableRoom();
        availableRoom.setId(id);
        Week week=new Week();
        Period period =new Period();
        Room room=new Room();
        week.setId(weekid);
        period.setId(timeid);
        room.setId(rid);
        availableRoom.setWeek(week);
        availableRoom.setPeriod(period);
        availableRoom.setRoom(room);
        try{
            int flag =availableRoomService.update(availableRoom);
            if(flag==0){
                throw new  RuntimeException();
            }

            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/detail")
    public AjaxJson toDetailPage(Integer id){
        AjaxJson ajaxJson=new AjaxJson();
        try {
            AvailableRoom availableRoom=availableRoomService.get(id);
            ajaxJson.getBody().put("availableRoom",availableRoom);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public AjaxJson deleteUser(Integer id){

        AjaxJson ajaxJson=new AjaxJson();
        try{
            availableRoomService.delete(id);
            ajaxJson.setMsg("删除成功");
            ajaxJson.setSuccess(true);
        }catch (Exception e){

            ajaxJson.setMsg("删除失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }



    @ResponseBody
    @RequestMapping("/deleteAvailableRooms")
    public AjaxJson deleteLessons(Integer[] id){
        AjaxJson ajaxJson=new AjaxJson();

        try{
            availableRoomService.deleteAvailableRooms(id);
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
    @GetMapping("/getAllAvailableRooms")
    public AjaxJson getAllAvailableRooms(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<AvailableRoom> availableRooms=availableRoomService.findAllList();
            ajaxJson.getBody().put("availableRooms",availableRooms);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/getAvailableRoomsByBuildingAndTime")
    public AjaxJson getAvailableRoomsByBuildingAndTime(Integer bid,Integer weekId,Integer timeId){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            Building building=new Building();
            building.setId(bid);
            List<AvailableRoom> availableRooms=availableRoomService.getAvailableRoomsByBuildingAndTime(building,weekId,timeId);
            ajaxJson.getBody().put("availableRooms",availableRooms);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


    @ResponseBody
    @PostMapping("/getAvailableRoomsByWeekIdAndTimeId")
    public AjaxJson getAvailableRoomsByWeekIdAndTimeId(Integer weekId,Integer timeId){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<AvailableRoom> availableRooms=availableRoomService.getAvailableRoomsByWeekIdAndTimeId(weekId,timeId);
            if(availableRooms.size()<0){

                ajaxJson.setMsg("此时间无教室");
                ajaxJson.setSuccess(false);
            }else{
                ajaxJson.getBody().put("availableRooms",availableRooms);
                ajaxJson.setSuccess(true);
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    @ResponseBody
    @GetMapping("/generateAvailableRoom")
    public AjaxJson generateAvailableRoom(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            if(availableRoomService.getAll()){
                int number= availableRoomService.generateAvailableRoom();
                ajaxJson.getBody().put("number",number);
                ajaxJson.setSuccess(true);
            }
            else {
                ajaxJson.setMsg("已有可用教室数据，请先删除已有数据");
                ajaxJson.setSuccess(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setMsg("批量插入失败");
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


    @ResponseBody
    @GetMapping("/findAllList")
    public AjaxJson findAllList(AvailableRoom availableRoom){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            List<List<AvailableRoom>> availableRoomLists = ExamUtils.getAvailableRoomLists();
            ajaxJson.getBody().put("list",availableRoomLists.size());
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


    @ResponseBody
    @GetMapping("/deleteAllAvailableRoom")
    public AjaxJson deleteAllAvailableRoom(){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            int number=availableRoomService.deleteAllAvailableRoom();
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;

    }

    @ResponseBody
    @PostMapping("/update")
    public AjaxJson update(AvailableRoom availableRoom){
        AjaxJson ajaxJson=new AjaxJson();
        try{
            int number=availableRoomService.update(availableRoom);
            ajaxJson.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;

    }

}


