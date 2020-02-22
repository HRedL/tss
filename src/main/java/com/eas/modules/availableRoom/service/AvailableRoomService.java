package com.eas.modules.availableRoom.service;

import com.eas.common.persistence.CrudService;
import com.eas.common.utils.RoomUtils;
import com.eas.modules.availableRoom.dao.AvailableRoomDao;
import com.eas.modules.availableRoom.entity.AvailableRoom;
import com.eas.modules.building.dao.BuildingDao;
import com.eas.modules.building.entity.Building;
import com.eas.modules.building.service.BuildingService;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.room.dao.RoomDao;
import com.eas.modules.room.entity.Room;
import com.eas.modules.room.service.RoomService;
import com.eas.modules.period.dao.PeriodDao;
import com.eas.modules.period.entity.Period;
import com.eas.modules.week.dao.WeekDao;
import com.eas.modules.week.entity.Week;
import com.eas.modules.week.service.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author why
 * @Version 2018/11/13
 */
@Service
public class AvailableRoomService extends CrudService<AvailableRoomDao,AvailableRoom> {

    @Autowired
    AvailableRoomDao availableRoomDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    WeekDao weekDao;
    @Autowired
    PeriodDao periodDao;
    @Autowired
    BuildingDao buildingDao;
    @Autowired
    DictDao dictDao;

    @Autowired
    RoomService roomService;

    @Autowired
    WeekService weekService;

    @Autowired
    BuildingService buildingService;

    @Override
    public AvailableRoom get(int id){
        AvailableRoom availableRoom=availableRoomDao.get(id);
        availableRoom.setRoom(roomService.get(availableRoom.getRoom().getId()));
        availableRoom.setWeek(weekDao.get(availableRoom.getWeek().getId()));
        availableRoom.setPeriod(periodDao.get(availableRoom.getPeriod().getId()));
        return availableRoom;
    }
    //此方法不从字典表中查出数据，专为初始化修改页面专用
    public AvailableRoom get1(int id){
        AvailableRoom availableRoom=availableRoomDao.get(id);
        Room room= roomDao.get(availableRoom.getRoom().getId());
        Building building=buildingDao.get(room.getBuilding().getId());
        room.setBuilding(building);
        availableRoom.setRoom(room);
        availableRoom.setWeek(weekDao.get(availableRoom.getWeek().getId()));
        availableRoom.setPeriod(periodDao.get(availableRoom.getPeriod().getId()));
        return availableRoom;
    }

//    public AvailableRoom getDetail(int id){
//        AvailableRoom availableRoom=availableRoomDao.get(id);
//        Room room= roomDao.get(availableRoom.getRoom().getId());
//        room.getBuilding().setCampus(dictDao.getDictByCondition("CAMPUS",room.getBuilding().getCampus()).getLabel());
//        room.getBuilding().setBnum(dictDao.getDictByCondition("BNUM",room.getBuilding().getBnum()).getLabel());
//        availableRoom.setRoom(room);
//        availableRoom.setWeek(weekDao.findWeekById(availableRoom.getWeek().getId()));
//        availableRoom.setPeriod(timeDao.get(availableRoom.getPeriod().getId()));
//        return availableRoom;
//    }

    @Override
    public int delete(int id){
        return super.dao.delete(id);
    }
    @Override
    public List<AvailableRoom> findAllList(){
        List<AvailableRoom> availableRooms=availableRoomDao.findAllList();
//        for(AvailableRoom availableRoom:availableRooms){
//            availableRoom.setRoom(roomService.get(availableRoom.getRoom().getId()));
//            availableRoom.setWeek(weekDao.get(availableRoom.getWeek().getId()));
//            availableRoom.setPeriod(timeDao.get(availableRoom.getPeriod().getId()));
//        }

        return availableRooms;
    }

    public List<AvailableRoom> findAllAvWithAllInfo(){

        List<Room> rooms=roomDao.findAllList();

        Map<Integer,Room> roomMap= RoomUtils.getMapWithKeyId(rooms);

        List<AvailableRoom> availableRoomList= availableRoomDao.findAllAvWithAllInfo();
        for(AvailableRoom availableRoom:availableRoomList){
            availableRoom.setRoom(roomMap.get(availableRoom.getRoom().getId()));
        }
        return availableRoomList;
    }



    @Override
    public List<AvailableRoom> findList(AvailableRoom entity) {
        List<AvailableRoom> availableRoomList = availableRoomDao.findList(entity);
        for(int i = 0; i < availableRoomList.size(); i++) {
            availableRoomList.set(i, get(availableRoomList.get(i).getId()));
        }
        return availableRoomList;
    }

    public List<AvailableRoom> findListByWTB(AvailableRoom entity) {
        List<AvailableRoom> availableRoomList = availableRoomDao.findListByWTB(entity);
        for(int i = 0; i < availableRoomList.size(); i++) {
            availableRoomList.set(i, get(availableRoomList.get(i).getId()));
        }
        return availableRoomList;
    }
    @Transactional
    @Override
    public int insert(AvailableRoom availableRoom) {
        return availableRoomDao.insert(availableRoom);
    }


    @Transactional
    @Override
    public int update(AvailableRoom availableRoom){
        return availableRoomDao.update(availableRoom);
    }



    @Override
    public AvailableRoom getT(AvailableRoom availableRoom) {

        return availableRoomDao.getT(availableRoom);
    }

    public void deleteAvailableRooms(Integer[] ids) {
        availableRoomDao.deleteAvailableRooms(ids);

    }

    public List<AvailableRoom> findAvailableRoomsByCondition1(String queryText) {
        List<AvailableRoom> availableRooms =null;

        //如果有查询条件就行条件查询
        if(queryText!=null) {
            //模糊查询出rooms和times
            List<Room> rooms = roomService.findRoomsByCondition(queryText);
            List<Period> periods = periodDao.findTimesByCondition(queryText);
            List<Week> weeks = weekDao.findListByText(queryText);
            //根据rooms和times查出availableRoom的集合
            List<AvailableRoom> availableRooms1=getAvailableRoomsByRooms(rooms);
            List<AvailableRoom> availableRooms2=getAvailableRoomsByTimes(periods);
            List<AvailableRoom> availableRooms3=getAvailableRoomsByWeeks(weeks);
            //取两集合的并集
            availableRooms=unionAvailableRooms(availableRooms3,unionAvailableRooms(availableRooms1,availableRooms2));

        }else{
            availableRooms=availableRoomDao.findAllList();
        }
        List<AvailableRoom> availableRoomList=new ArrayList<>();


        //根据表中的外键id补全availableRoom对象的信息。
        for (AvailableRoom availableRoom : availableRooms) {
            Room room= roomService.get(availableRoom.getRoom().getId());
//            room.getBuilding().setCampus(campus.get(room.getBuilding().getCampus()));
//            room.getBuilding().setBnum(bnums.get(room.getBuilding().getBnum()));
            availableRoom.setRoom(room);
            availableRoom.setWeek(weekDao.get(availableRoom.getWeek().getId()));
            availableRoom.setPeriod(periodDao.get(availableRoom.getPeriod().getId()));

            availableRoomList.add(availableRoom);
        }


        return availableRoomList;
    }

    public List<AvailableRoom> findAvailableRoomsByCondition(Map<String,Object> map) {
        List<AvailableRoom> availableRooms =null;


        String queryText=map.get("queryText").toString();
        String queryType=map.get("queryType").toString();
        if("all".equals(queryType)){
            availableRooms=availableRoomDao.findAvailableRoomsByCondition(map);
        }else if ("date".equals(queryType)){
            List<Week> weeks =weekDao.findListByText(queryText);
            if(weeks.size()>0){
                map.put("weeks",weeks);
            }else{
                map.put("weeks",null);
            }
            availableRooms=availableRoomDao.findAvailableRoomsByCondition(map);
        }else if("period".equals(queryType)){
            List<Period> periods = periodDao.findListByText(queryText);
            if(periods.size()>0){
                map.put("times", periods);
            }else{
                map.put("times",null);
            }

            map.put("times", periods);
            availableRooms=availableRoomDao.findAvailableRoomsByCondition(map);
        }else if("campus".equals(queryType)){
            List<Building> buildings=buildingService.getBuildingByCampus(queryText);
            if(buildings.size()>0){
                List<Room> rooms=roomService.getRoomByBuildings(buildings);
                if(rooms.size()>0){
                    map.put("rooms",rooms);
                }
                else{
                    map.put("rooms",null);
                }
            }else{
                map.put("rooms",null);
            }
            availableRooms=availableRoomDao.findAvailableRoomsByCondition(map);
        }else if("building".equals(queryType)){
            List<Building> buildings=buildingService.findBuildingsByCondition(queryText);
            if(buildings.size()>0){
                List<Room> rooms=roomService.getRoomByBuildings(buildings);
                if(rooms.size()>0){
                    map.put("rooms",rooms);
                }else{
                    map.put("rooms",null);
                }
            }else{
                map.put("rooms",null);
            }
            availableRooms=availableRoomDao.findAvailableRoomsByCondition(map);
        }else if("room".equals(queryType)){
            List<Room> rooms=roomService.getRoomsByRnum(queryText);
            if(rooms.size()>0){
                map.put("rooms",rooms);
            }else{
                map.put("rooms",null);
            }
            availableRooms=availableRoomDao.findAvailableRoomsByCondition(map);
        }

        //这个地方的sql也很多。。所以还要改
        //根据表中的外键id补全availableRoom对象的信息。
        for (AvailableRoom availableRoom : availableRooms) {
            Room room= roomService.get(availableRoom.getRoom().getId());
            availableRoom.setRoom(room);
            availableRoom.setWeek(weekDao.get(availableRoom.getWeek().getId()));
            availableRoom.setPeriod(periodDao.get(availableRoom.getPeriod().getId()));
        }



        return availableRooms;

    }


    /**
     * 根据times集合查询availableRoom集合
     */
    private List<AvailableRoom> getAvailableRoomsByTimes(List<Period> periods){
        List<AvailableRoom> availableRooms=new ArrayList<>();
        for (Period period : periods) {
            AvailableRoom availableRoom = new AvailableRoom();
            availableRoom.setPeriod(period);
            List<AvailableRoom> availableRooms1 = availableRoomDao.getAvailableRooms(availableRoom);
            if (availableRooms1.size() != 0) {
                availableRooms.addAll(availableRooms1);
            }
        }
        return availableRooms;
    }

    /**
     * 根据weeks集合查询availableRoom集合
     */
    private List<AvailableRoom> getAvailableRoomsByWeeks(List<Week> weeks){
        List<AvailableRoom> availableRooms=new ArrayList<>();
        for (Week week : weeks) {
            AvailableRoom availableRoom = new AvailableRoom();
            availableRoom.setWeek(week);
            List<AvailableRoom> availableRooms1 = availableRoomDao.getAvailableRooms(availableRoom);
            if (availableRooms1.size() != 0) {
                availableRooms.addAll(availableRooms1);
            }
        }
        return availableRooms;
    }

    /**
     * 根据rooms集合查询availableRoom集合
     */
    private List<AvailableRoom> getAvailableRoomsByRooms(List<Room> rooms) {
        List<AvailableRoom> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            AvailableRoom availableRoom = new AvailableRoom();
            availableRoom.setRoom(room);
            List<AvailableRoom> availableRooms1 = availableRoomDao.getAvailableRooms(availableRoom);

            if (availableRooms1.size() != 0) {
                availableRooms.addAll(availableRooms1);
            }
        }
        return availableRooms;
    }

    /**
     *取两个集合的并集
     * 本来好像能重写equal方法，调用List里面的remove方法来弄
     */
    private List<AvailableRoom> unionAvailableRooms(List<AvailableRoom> availableRooms1, List<AvailableRoom> availableRooms2){

        if(availableRooms1.size()==0){
            //如果集合1,2都为空，那么直接返回一个空的集合对象
            if(availableRooms2.size()==0){
                return new ArrayList<>();
            //如果集合1为空，集合2不为空，那么直接返回集合2
            }else{
                return availableRooms2;
            }
        }
        //如果集合1不为空，集合2为空，那么直接返回集合1
        if(availableRooms2.size()==0){
            return availableRooms1;
        }
        //如果集合1,2都不为空
        List<AvailableRoom> availableRooms = availableRooms1;
        for (AvailableRoom availableRoom2 : availableRooms2) {
            boolean equal=false;
            for (AvailableRoom availableRoom1 : availableRooms1) {
                //如果集合里里已经有，则设置标志位为true，结束当前循环
                if (availableRoom2.getId().equals(availableRoom1.getId())) {
                    equal=true;
                    break;
                }
            }
            //如果集合里没有，那么保存下来该元素
            if(!equal){
                availableRooms.add(availableRoom2);
            }
        }
        return availableRooms;
    }


    public List<AvailableRoom> getAvailableRoomsByWeekIdAndTimeId(Integer weeId, Integer timeId) {
        AvailableRoom availableRoom=new AvailableRoom();
        if(weeId!=-1){
            Week week=new Week();
            week.setId(weeId);
            availableRoom.setWeek(week);
        }
        if(timeId!=-1){
            Period period =new Period();
            period.setId(timeId);
            availableRoom.setPeriod(period);
        }
        List<AvailableRoom> availableRooms=availableRoomDao.getAvailableRooms(availableRoom);
        for(AvailableRoom availableRoom1:availableRooms){
            availableRoom1.setRoom(roomService.get(availableRoom1.getRoom().getId()));
        }

        return availableRooms;
    }

    @Transactional
    public int generateAvailableRoom() {
        List<Room> rooms=roomService.findAllList();
        List<Week> weeks=weekDao.findAllList();
        List<Period> periods = periodDao.findAllList();
        List<AvailableRoom> availableRooms=new ArrayList<>();
        //三个循环，，，，，这个效率低啊,如何解决一下子呢。。。
        for(Room room:rooms){
            for (Week week:weeks){
                for(Period period : periods){
                    AvailableRoom availableRoom=new AvailableRoom();
                    availableRoom.setRoom(room);
                    availableRoom.setWeek(week);
                    availableRoom.setPeriod(period);
                    availableRoom.setFlag(0);
                    availableRooms.add(availableRoom);
                }
            }
        }
        return availableRoomDao.batchInset(availableRooms);

    }

    public int deleteAllAvailableRoom() {
        return availableRoomDao.deleteAllAvailableRoom();
    }

    public int getCount(Map<String, Object> map) {
        int count=0;

        String queryText=map.get("queryText").toString();
        String queryType=map.get("queryType").toString();
        if("all".equals(queryType)){
            count=availableRoomDao.getCount(map);
        }else if ("date".equals(queryType)){
            List<Week> weeks =weekDao.findListByText(queryText);
            if(weeks.size()>0){
                map.put("weeks",weeks);
            }else{
                map.put("weeks",null);
            }
            count=availableRoomDao.getCount(map);
        }else if("period".equals(queryType)){
            List<Period> periods = periodDao.findListByText(queryText);
            if(periods.size()>0){
                map.put("times", periods);
            }else{
                map.put("times",null);
            }

            map.put("times", periods);
            count=availableRoomDao.getCount(map);
        }else if("campus".equals(queryType)){
            List<Building> buildings=buildingService.getBuildingByCampus(queryText);
            if(buildings.size()>0){
                List<Room> rooms=roomService.getRoomByBuildings(buildings);
                if(rooms.size()>0){
                    map.put("rooms",rooms);
                }
                else{
                    map.put("rooms",null);
                }
            }else{
                map.put("rooms",null);
            }
            count=availableRoomDao.getCount(map);
        }else if("building".equals(queryType)){
            List<Building> buildings=buildingService.findBuildingsByCondition(queryText);
            if(buildings.size()>0){
                List<Room> rooms=roomService.getRoomByBuildings(buildings);
                if(rooms.size()>0){
                    map.put("rooms",rooms);
                }else{
                    map.put("rooms",null);
                }
            }else{
                map.put("rooms",null);
            }
            count=availableRoomDao.getCount(map);

        }else if("room".equals(queryType)){
            List<Room> rooms=roomService.getRoomsByRnum(queryText);
            if(rooms.size()>0){
                map.put("rooms",rooms);
            }else{
                map.put("rooms",null);
            }
            count=availableRoomDao.getCount(map);
        }
        return count;

    }

    public List<AvailableRoom> getAvailableRoomByRid(Integer rid) {
        Room room=new Room();
        room.setId(rid);
        AvailableRoom availableRoom=new AvailableRoom();
        availableRoom.setRoom(room);

        return availableRoomDao.getAvailableRooms(availableRoom);
    }

    public List<AvailableRoom> getAvailableRooms(AvailableRoom availableRoom) {

        return availableRoomDao.getAvailableRooms(availableRoom);
    }

    public List<AvailableRoom> getAvailableRoomsByBuildingAndTime(Building building, Integer weekId, Integer timeId) {

        List<Room> rooms=roomService.getRoomsByBid(building.getId());
        Map<String,Object> map=new HashMap<>();
        map.put("rooms",rooms);
        map.put("weekId",weekId);
        map.put("periodId",timeId);
        List<AvailableRoom> availableRooms=availableRoomDao.getAvailableRoomsByBuildingAndTime(map);


        for (AvailableRoom availableRoom : availableRooms) {
            Room room = roomService.get(availableRoom.getRoom().getId());
            availableRoom.setRoom(room);
        }
        availableRooms= orderByRnum(availableRooms);
        return availableRooms;
    }

    private List<AvailableRoom> orderByRnum(List<AvailableRoom> availableRooms){
        Map<Integer,AvailableRoom> availableRoomMap=new HashMap<>();


        List<Integer> rnumsList=new ArrayList<>();

        for(AvailableRoom availableRoom:availableRooms){
            int rnum=Integer.parseInt(availableRoom.getRoom().getRnum());
            availableRoomMap.put(rnum,availableRoom);
            rnumsList.add(rnum);
        }
        Integer[] rnums=new Integer[rnumsList.size()];
        rnumsList.toArray(rnums);

        int temp=0;
        for(int i=0;i<rnums.length-1;i++){//冒泡趟数
            for(int j=0;j<rnums.length-i-1;j++){
                if(rnums[j+1]<rnums[j]){
                    temp = rnums[j];
                    rnums[j] = rnums[j+1];
                    rnums[j+1] = temp;
                }
            }
        }
        List<AvailableRoom> availableRoomList=new ArrayList<>();
        for(int rnum:rnums){
             availableRoomList.add(availableRoomMap.get(rnum));
        }

        return availableRoomList;
    }

    public List<AvailableRoom> findAvailableRoomsByIds(List<Integer> availableRoomsId){
        List<AvailableRoom> availableRoomList=availableRoomDao.findAvailableRoomsByIds(availableRoomsId);

        List<Integer> roomIds=new ArrayList<>();
        for (AvailableRoom availableRoom : availableRoomList) {
            roomIds.add(availableRoom.getRoom().getId());
        }

        List<Room> rooms=roomService.findRoomsByIds(roomIds);
        Map<Integer,Room> roomMap=new HashMap<>();
        for (Room room:rooms){
            roomMap.put(room.getId(),room);
        }

        for (AvailableRoom availableRoom : availableRoomList) {
            availableRoom.setRoom(roomMap.get(availableRoom.getRoom().getId()));
        }

        return availableRoomList;
    }


    public void updateList(List<AvailableRoom> availableRoomList) {
        availableRoomDao.updateList(availableRoomList);

    }
    public Boolean getAll(){
        if(availableRoomDao.getAll().size()==0||availableRoomDao.getAll()==null){
            return true;
        }
        else return false;
    }
}

