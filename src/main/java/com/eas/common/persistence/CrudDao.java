/**
 * 
 */
package com.eas.common.persistence;

import java.util.List;

/**
 * CrudDao create|read|update|delete
 * @author lilinzhen
 * @version 2018年3月4日
 */
public interface CrudDao<T> extends BaseDao {
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	T get(int id);

	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	T getT(T entity);


	/**
	 * 查询数据列表（主要用在含有条件查询的场景）
	 * @param entity
	 * @return
	 */
	List<T> findList(T entity);
	
	/**
	 * 查询所有数据列表（一般用不到）
	 * @return
	 */
	List<T> findAllList();
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	int insert(T entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	int update(T entity);

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	int delete(int id);
	
}
