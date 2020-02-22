package com.eas.modules.publicSession.dao;

import com.eas.common.annotation.MybatisDao;
import com.eas.common.persistence.CrudDao;
import com.eas.modules.publicSession.entity.PublicSession;

import java.util.List;

/**
 * @Author huahonglei
 * @Version 2019/6/28
 */
@MybatisDao
public interface PublicSessionDao extends CrudDao<PublicSession> {


    int batchInsert(List<PublicSession> publicSessions);

}
