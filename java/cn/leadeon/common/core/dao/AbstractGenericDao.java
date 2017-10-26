package cn.leadeon.common.core.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.Assert;

import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("unchecked")
public abstract class AbstractGenericDao<T> extends SqlMapClientDaoSupport
		implements ApplicationContextAware {

	protected Class<T> clazz;

	protected ApplicationContext context;

	protected ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

	/**
	 * 切换数据源
	 *
	 * @param name
	 *            传递sqlMapClient或sqlMapClient2
	 * @see
	 */
	public void choseSqlClient(String name) {
		SqlMapClient client = (SqlMapClient) getContext().getBean(name);
		setSqlMapClientTemplate(new SqlMapClientTemplate(client));
		afterPropertiesSet();
	}

	/**
	 * map查询，若涉及分页请不要调用此方法
	 *
	 * @param map
	 *            包含各种属性的查询
	 */
	@SuppressWarnings("rawtypes")
	public List<T> find(Map params, int... pageParams) {
		if ((pageParams != null) && (pageParams.length > 0)) { // 如果页面参数为空
			int rowStart = 0;
			int pageSize = 0;
			rowStart = Math.max(0, pageParams[0]);
			if (pageParams.length > 1) {
				pageSize = Math.max(0, pageParams[1]);
			}
			return this.findForPage(params, rowStart, pageSize);
		} else {
			return this.getSqlMapClientTemplate().queryForList(
					getNameSpace() + ".find", params);
		}

	}

	/**
	 * 分页查询函数，使用List.
	 *
	 * @param pageNo页号
	 *            ,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	@SuppressWarnings("rawtypes")
	private List findForPage(Map params, int rowStart, int pageSize) {
		Assert.isTrue(rowStart >= 0, "rowStart should start from 0");
		List list = getSqlMapClientTemplate().queryForList(
				getNameSpace() + ".find", params, rowStart, pageSize);
		return list;

	}

	/**
	 * 查询对象
	 */
	public T get(Serializable id) {
		T entity = (T) getSqlMapClientTemplate().queryForObject(
				getNameSpace() + ".get", id);
		return entity;
	}

	// 通过范型反射，取得在子类中定义的class.
	protected String  getNameSpace() {
		clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return clazz.getSimpleName();
	}

	/**
	 * 查询总数
	 */
	@SuppressWarnings("rawtypes")
	public Long getTotalCount(Map params) {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
				getNameSpace() + ".totalCount", params);
		return (count == null ? 0 : count);
	}

	/**
	 * 根据ID删除对象
	 */
	public void removeById(Serializable id) {
		getSqlMapClientTemplate().delete(getNameSpace() + ".delete", id);
	}

	/**
	 * 新增对象
	 */
	public T save(T entity) {
		getSqlMapClientTemplate().insert(getNameSpace() + ".save", entity);
		return entity;
	}

	/**
	 * 更新对象
	 */
	public T update(T entity) {
		getSqlMapClientTemplate().update(getNameSpace() + ".update", entity);
		return entity;
	}


	/**
	 * 获取主键的最大id加1
	 *
	 * @return
	 */
	public synchronized Integer getMaxIdIncrease() {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				getNameSpace() + ".getMaxIdIncrease");
		return count;
	}

	/**
	 * 获取主键的最大id
	 *
	 * @return
	 */
	public synchronized Integer getMaxId() {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				getNameSpace() + ".getMaxId");
		return count;
	}

	/**
	 *
	 * 用于mysql分页查询
	 *
	 * @param params
	 * @param pageParams
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("rawtypes")
	public List<T> findPaging(Map params, int... pageParams) {
		if ((pageParams != null) && (pageParams.length > 0)) { // 如果页面参数为空
			int rowStart = 0;
			int pageSize = 0;
			rowStart = Math.max(0, pageParams[0]);
			if (pageParams.length > 1) {
				pageSize = Math.max(0, pageParams[1]);
			}
			params.put("rowStart", rowStart);
			params.put("pageSize", pageSize);
			List list = getSqlMapClientTemplate().queryForList(
					getNameSpace() + ".find", params);
			return list;
		} else {
			return this.getSqlMapClientTemplate().queryForList(
					getNameSpace() + ".find", params);
		}

	}

}
