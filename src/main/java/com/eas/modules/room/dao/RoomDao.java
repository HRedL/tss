package com.eas.modules.room.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.building.entity.Building;
import com.eas.modules.room.entity.Room;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @Author hwq
 * @Version 2018/10/20
 */
@MybatisDao
public interface RoomDao extends CrudDao<Room> {

    @Override
    public Room get(int id);

    @Override
    public List<Room> findAllList();/*获取全部数据*/

    @Override
    int delete(int id);/*删除指定数据*/

    @Override
    int insert(Room room);/*插入数据*/

    @Override
    int update(Room room);/*更新数据*/


    @Override
    Room getT(Room entity);

    List<Room> findRoomsByCondition(@Param(value="queryText")String queryText);

    List<Room> getRoomsByBid(int bid);

    List<Room> findListByText(@Param(value = "queryText") String queryText);

    void deleteRooms(Integer[] ids);

    List<Room> getRoomByBuildings(List<Building> buildings);

    List<Room> getRooms(Room room);

    List<Room> getRoomsByRnum(@Param(value = "queryText")String queryText);

    List<Room> findRoomsByIds(List<Integer> roomIds);
}
