package cn.leadeon.common.core.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author linxuchao
 * 
 * @param <T>
 */
public interface BaseEntityManager<T> {

	/**
	 * 创建实体
	 * 
	 * @param entity
	 */
	void create(T entity);

	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	void delete(T entity);

	/**
	 * 查询
	 * 
	 * @param params
	 * @param pageParams
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<T> find(Map params, int... pageParams);

	/**
	 * 获取一个实体
	 * 
	 * @param id
	 * @return
	 */
	T get(Integer id);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	void update(T entity);

}
