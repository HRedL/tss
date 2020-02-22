package com.eas.modules.user.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户dao
 * @Author mqy
 * @Version 2018/10/23
 */
@MybatisDao
public interface UserDao extends CrudDao<User> {

    @Override
    User get(int id);

    @Override
    List<User> findAllList();

    @Override
    int delete(int id);

    @Override
    int insert(User entity);

    @Override
    int update(User entity);

    /**
     * 根据用户id查询用户信息
     * @param id 用户id
     * @return 返回与用户id匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findUserById(Integer id);

    /**
     * 根据用户账号查询用户信息
     * @param logname 用户账号
     * @return 返回与用户账号匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findUserByLogname(String logname);

    User findUserByLognameAndType(
            @Param("logname") String logname,
            @Param("type")    String type
    );

    List<User> findUserByCondition(@Param(value = "queryText") String queryText);
    public void deleteUsers(Integer[] id);
    @Override
    User getT(User user);
}
