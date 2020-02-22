package com.eas.modules.building.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.building.entity.Building;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author hwq
 * @Version 2018/10/20
 */
@MybatisDao
public interface BuildingDao extends CrudDao<Building> {

    @Override
    public Building get(int id);

    @Override
    public List<Building> findAllList();/*获取全部数据*/

    @Override
    int delete(int id);/*删除指定数据*/

    @Override
    int insert(Building building);/*插入数据*/

    @Override
    int update(Building building);/*更新数据*/

    @Override
    Building getT(Building building);

    List<Building> getBuildingByCampus(@Param("campus") String campus);

    List<Building > getBuildingByBnum(@Param("bnum")String bnum);
}

