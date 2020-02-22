package com.eas.modules.building.service;

import com.eas.common.persistence.CrudService;
import com.eas.common.utils.DictUtil;
import com.eas.modules.building.dao.BuildingDao;
import com.eas.modules.building.entity.Building;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.dict.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author hwq
 * @Version 2018/10/22
 */
@Service
public class BuildingService extends CrudService<BuildingDao,Building> {

    @Autowired
    BuildingDao buildingDao;

    @Autowired
    DictDao dictDao;

    @Override
    public Building get(int id)  {
        Building building=buildingDao.get(id);
        List<Dict> dicts1=dictDao.getDictsByCondition("CAMPUS");
        List<Dict> dicts2=dictDao.getDictsByCondition("BNUM");
        Map<String,String> campus= DictUtil.getMap(dicts1);
        Map<String,String> bnums=DictUtil.getMap(dicts2);
        building.setCampus(campus.get(building.getCampus()));
        building.setBnum(bnums.get(building.getBnum()));
        return building;

    }

    @Override
    public List<Building> findAllList() {
        return super.findAllList();
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public int insert(Building building) {
        return super.insert(building);
    }

    @Override
    public int update(Building building) {
        return super.update(building);
    }

    public List<Building> getBuildingByCampus(String campus) {
        //根据campus查出所有的building
        List<Building> buildings=buildingDao.getBuildingByCampus(campus);
        //将building里面的bnum设置为dict表中的值
        List<Dict> dicts=dictDao.getDictsByCondition("BNUM");
        Map<String,String> buildingsMap= DictUtil.getMap(dicts);
        for(Building building:buildings){
            building.setBnum(buildingsMap.get(building.getBnum()));
        }
        return buildings;
    }

    public List<Building> findBuildingsByCondition(String queryText) {

        List<Dict> campusDict= dictDao.getDictsByTypeAndLabel("CAMPUS",queryText);
        List<Dict> bnumDict=dictDao.getDictsByTypeAndLabel("BNUM",queryText);
        if(campusDict.size()==0){
            //如果集合1,2都为空，那么直接返回一个空的集合对象
            if(bnumDict.size()==0){
                return new ArrayList<>();
                //如果集合1为空，集合2不为空，那么直接返回集合2
            }else{
                List<Building> buildings=new ArrayList<>();
                for(Dict dict:bnumDict){
                    List<Building> buildings1=buildingDao.getBuildingByBnum(dict.getValue());
                    if(buildings1.size()>0){
                        buildings.addAll(buildings1);
                    }
                }
                return buildings;
            }
        }
        //如果集合1不为空，集合2为空，那么直接返回集合1
        if(bnumDict.size()==0){
            List<Building> buildings=new ArrayList<>();
            for(Dict dict:campusDict){
                List<Building> buildings1=buildingDao.getBuildingByCampus(dict.getValue());
                if(buildings1.size()>0){
                    buildings.addAll(buildings1);
                }
            }
            return buildings;
        }
        //都不为空

        List<Building> buildings11=new ArrayList<>();
        List<Building> buildings22=new ArrayList<>();
        for(Dict dict:campusDict){
            List<Building> buildings1=buildingDao.getBuildingByCampus(dict.getValue());
            if(buildings1.size()>0){
                buildings11.addAll(buildings1);
            }
        }
        for(Dict dict:bnumDict){
            List<Building> buildings1=buildingDao.getBuildingByBnum(dict.getValue());
            if(buildings1.size()>0){
                buildings22.addAll(buildings1);
            }
        }


        return unionBuildings(buildings11,buildings22);

    }

    private List<Building> unionBuildings(List<Building> buildings1,List<Building> buildings2){

        if(buildings1.size()==0){
            //如果集合1,2都为空，那么直接返回一个空的集合对象
            if(buildings2.size()==0){
                return new ArrayList<>();
                //如果集合1为空，集合2不为空，那么直接返回集合2
            }else{
                return buildings2;
            }
        }
        //如果集合1不为空，集合2为空，那么直接返回集合1
        if(buildings2.size()==0){
            return buildings1;
        }
        //如果集合1,2都不为空
        List<Building> buildings = buildings1;
        for (Building building2 : buildings2) {
            boolean equal=false;
            for (Building building1 : buildings1) {
                //如果集合里里已经有，则设置标志位为true，结束当前循环
                if (building2.getId().equals(building1.getId())) {
                    equal=true;
                    break;
                }
            }
            //如果集合里没有，那么保存下来该元素
            if(!equal){
                buildings.add(building2);
            }
        }
        return buildings;
    }
}

