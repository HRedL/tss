package com.eas.modules.dict.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.dict.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典Dao
 * @Author mqy
 * @Version 2018/11/6
 */
@MybatisDao
public interface DictDao extends CrudDao<Dict> {
    @Override
    Dict get(int id);/*获取指定数据*/

    @Override
    List<Dict> findAllList();/*获取全部数据*/

    @Override
    int delete(int id);/*删除指定数据*/

    @Override
    int insert(Dict entity);/*插入数据*/

    @Override
    int update(Dict entity);/*更新数据*/

    List<Dict> getDictsByCondition(@Param("type") String type);

    Dict getDictByCondition(@Param("type")String type,@Param("value")String value);




    List<Dict> getDictsByTypeAndLabel(@Param("type") String type,@Param("label") String label);

    Dict getDictByTypeAndLabel(@Param("type") String type,@Param("label") String label);

    /**
     * 通过type查找所有dict
     */
    List<Dict> getDictsByType(@Param("type") String type);

    List<Dict> getDictsByTypeAndParentId(@Param("type") String type,@Param("ptype") String ptype,@Param("pid") String pid);
}
