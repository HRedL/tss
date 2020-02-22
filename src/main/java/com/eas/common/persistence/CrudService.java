/**
 * 
 */
package com.eas.common.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * CrudService create|read|update|delete
 * @author lilinzhen
 * @version 2018年3月4日
 */
public abstract class CrudService<D extends CrudDao<T>, T> extends BaseService{

	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(int id) {
		return dao.get(id);
	}

	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T getT(T entity) {
		return dao.getT(entity);
	}

	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}

	/**
	 * 查询列表的所有数据
	 * @return
	 */
	public List<T> findAllList(){
		return dao.findAllList();
	}

	/**
	 * 插入数据
	 * @param entity
	 */
	public int insert(T entity) {
		return dao.insert(entity);
	}
	
	/**
	 * 更新数据
	 * @param entity
	 */
	public int update(T entity) {
		return dao.update(entity);
	}

	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	public int delete(int id) {
		return dao.delete(id);
	}

	/**
	 * 批量删除数据
	 * @param ids
	 * @return
	 */
	public int deleteAll(int[] ids) {
		int rows=0;
		for (int id : ids) {
			rows+=delete(id);
		}
		return rows;
	}

}
