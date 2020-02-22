package com.eas.modules.room.service;

import com.eas.common.persistence.CrudService;
import com.eas.modules.building.dao.BuildingDao;
import com.eas.modules.building.entity.Building;
import com.eas.modules.building.service.BuildingService;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.room.dao.RoomDao;
import com.eas.modules.room.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hwq
 * @Version 2018/10/23
 */
@Service
public class RoomService extends CrudService<RoomDao,Room> {

    @Autowired
    RoomDao roomDao;
    @Autowired
    BuildingDao buildingDao;

    @Autowired
    BuildingService buildingService;

    @Autowired
    DictDao dictDao;
    @Override
    public Room get(int id) {
        Room room=roomDao.get(id);
        room.setBuilding(buildingService.get(room.getBuilding().getId()));
        return room;
    }

    public Room getWithoutDict(int id) {
        Room room=roomDao.get(id);
        //room.setBuilding(buildingService.get(room.getBuilding().getId()));
        return room;
    }

    @Override
    public int delete(int id){
        return super.dao.delete(id);
    }
    @Override
    public List<Room> findAllList(){
        return super.dao.findAllList();
    }
    @Override
    public int insert(Room room){return super.insert(room);}

    @Override
    public int update(Room room){return super.update(room);}

    public List<Room> findRoomsByCondition(String queryText){
        List<Room> rooms1=roomDao.findRoomsByCondition(queryText);
        List<Building> buildings= buildingService.findBuildingsByCondition(queryText);
        List<Room> rooms2=new ArrayList<>();
        for(Building building:buildings){
            List<Room> rooms3=roomDao.getRoomsByBid(building.getId());
            if(rooms3.size()>0){
                rooms2.addAll(rooms3);
            }
        }
        return unionRooms(rooms1,rooms2);
    }


    public List<Room> getRoomsByRnum(String queryText){
        return roomDao.getRoomsByRnum(queryText);
    }


    public List<Room> getRooms(Room room){
        return roomDao.getRooms(room);
    }

    private List<Room> unionRooms(List<Room> rooms1,List<Room> rooms2){

        if(rooms1.size()==0){
            //如果集合1,2都为空，那么直接返回一个空的集合对象
            if(rooms2.size()==0){
                return new ArrayList<>();
                //如果集合1为空，集合2不为空，那么直接返回集合2
            }else{
                return rooms2;
            }
        }
        //如果集合1不为空，集合2为空，那么直接返回集合1
        if(rooms2.size()==0){
            return rooms1;
        }
        //如果集合1,2都不为空
        List<Room> rooms = rooms1;
        for (Room room2 : rooms2) {
            boolean equal=false;
            for (Room room1 : rooms1) {
                //如果集合里里已经有，则设置标志位为true，结束当前循环
                if (room2.getId().equals(room1.getId())) {
                    equal=true;
                    break;
                }
            }
            //如果集合里没有，那么保存下来该元素
            if(!equal){
                rooms.add(room2);
            }
        }
        return rooms;
    }

    public List<Room> getRoomsByBid(Integer bid) {
//        for(Room room:rooms){
//            String rnum=room.getRnum();
//            room.setFloor(Integer.parseInt(rnum)/100);
//        }
        return roomDao.getRoomsByBid(bid);
    }

    public List<Room> findListByText(String queryText) {

        List<Room> rooms= roomDao.findListByText(queryText);
        if(rooms.size()>0) {
//            List<Dict> dicts=dictDao.getDictsByCondition("LTYPE");
//            Map<String,String> dictMap= DictUtil.getMap(dicts);
            for (Room room : rooms) {
                //lesson.setType(dictMap.get(lesson.getType()));
                room.setBuilding(buildingService.get(room.getBuilding().getId()));

            }
        }
        return rooms;
    }

    public void deleteRooms(Integer[] ids) {
        roomDao.deleteRooms(ids);
    }

    public List<Room> getRoomByBuildings(List<Building> buildings) {
        return roomDao.getRoomByBuildings(buildings);
    }


    public List<Room> findRoomsByIds(List<Integer> roomIds) {
        List<Room> rooms=roomDao.findRoomsByIds(roomIds);
        for(Room room:rooms){
            //这里还可以优化，先放这
            room.setBuilding(buildingService.get(room.getBuilding().getId()));
        }

        return rooms;

    }
}

