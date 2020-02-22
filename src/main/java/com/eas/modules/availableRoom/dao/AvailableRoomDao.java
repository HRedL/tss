package com.eas.modules.availableRoom.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.availableRoom.entity.AvailableRoom;

import java.util.List;
import java.util.Map;

/**
 * @Author lenovo
 * @Version 2018/10/19
 */

@MybatisDao
public interface AvailableRoomDao extends CrudDao<AvailableRoom> {
    @Override
    public AvailableRoom get(int id);/*获取指定数据*/

    @Override
    public List<AvailableRoom> findAllList();/*获取全部数据*/

    @Override
    public int delete(int id);/*删除指定数据*/

    @Override
    public int update(AvailableRoom availableRoom);

    public int updateRoom(AvailableRoom availableRoom);

    @Override
    int insert(AvailableRoom availableRoom);

    @Override
    List<AvailableRoom> findList(AvailableRoom entity);

    List<AvailableRoom> findListByWTB(AvailableRoom entity);

    @Override
    AvailableRoom getT(AvailableRoom availableRoom);

    void deleteAvailableRooms(Integer[] ids);

    List<AvailableRoom> getAvailableRooms(AvailableRoom availableRoom);

    int batchInset(List<AvailableRoom> availableRooms);

    int deleteAllAvailableRoom();

    List<AvailableRoom> findAvailableRoomsByCondition(Map<String, Object> map);

    Integer getCount(Map<String, Object> map);

    List<AvailableRoom> getAvailableRoomsByBuildingAndTime(Map<String, Object> map);

    List<AvailableRoom> findAvailableRoomsCriteria(Map<String, Object> map1);

    List<AvailableRoom> findAvailableRoomsByIds(List<Integer> availableRoomIds);

    List<AvailableRoom> findAllAvWithAllInfo();

    void updateList(List<AvailableRoom> availableRoomList);
    List<AvailableRoom> getAll();
}
