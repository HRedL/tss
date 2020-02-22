package com.eas.modules.dict.service;

import com.eas.common.persistence.CrudService;
import com.eas.modules.dict.dao.DictDao;
import com.eas.modules.dict.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典Service
 * @Author mqy
 * @Version 2018/11/6
 */
@Service
public class DictService extends CrudService<DictDao,Dict> {

   @Autowired
   DictDao dictDao;

    @Override
    public Dict get(int id) {
        return super.get(id);
    }

    @Override
    public List<Dict> findAllList() {
        return super.findAllList();
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public int insert(Dict entity) {
        return super.insert(entity);
    }

    @Override
    public int update(Dict entity) {
        return super.update(entity);
    }

    public List<Dict> getDictsByCondition(String type) {
        return dictDao.getDictsByCondition(type);
    }

    public List<Dict> getDictsByType(String type){
        return dictDao.getDictsByType(type);
    }

    public List<Dict> getDictsByTypeAndParentId(String type, String ptype,String pid) {

        return dictDao.getDictsByTypeAndParentId(type,ptype,pid);
    }
}
