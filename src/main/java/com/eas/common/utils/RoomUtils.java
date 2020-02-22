package com.eas.common.utils;

import com.eas.modules.room.entity.Room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author huahonglei
 * @Version 2019/6/5
 */
public class RoomUtils {



    public static Map<Integer,Room> getMapWithKeyId(List<Room> rooms){
        Map<Integer,Room> map=new HashMap<>();
        for(Room room:rooms){
            map.put(room.getId(),room);
        }
        return map;
    }
}
