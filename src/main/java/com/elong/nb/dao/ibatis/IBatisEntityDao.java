package com.elong.nb.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.elong.nb.common.util.GenericsUtils;
import com.elong.nb.dao.ibatis.model.Page;

/**
 * 负责为单个Entity 提供CRUD操作的IBatis DAO基类.
 * <p/>
 * 子类只要在类定义时指定所管理Entity的Class, 即拥有对单个Entity对象的CRUD操作.
 * 
 * <pre>
 * public class UserManagerIbatis extends IBatisEntityDao&lt;User&gt; {
 * }
 * </pre>
 * 
 * @author suwei
 * @see IBatisGenericDao
 */
public class IBatisEntityDao<T> extends IBatisGenericDao implements
		EntityDao<T> {

	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * DAO所管理的Entity类型.
	 */
	protected Class<T> entityClass;

	protected String primaryKeyName;

	/**
	 * 在构造函数中将泛型T.class赋给entityClass.
	 */
	@SuppressWarnings("unchecked")
	public IBatisEntityDao() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(String name, Object value) {
		return findBy(getEntityClass(), name, value);
	}

	/**
	 * 根据ID获取对象.
	 */
	public T get(Integer id) {
		return get(getEntityClass(), id);
	}

	/**
	 * 根据ID获取对象.
	 */
	public T get(String id) {
		return get(getEntityClass(), id);
	}

	@Override
	public T get(String selectName, Object obj) {
		return super.get(selectName, obj);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		return getAll(getEntityClass());
	}

	public List<T> getAll(Map map) {
		return getAll(getEntityClass(), map);
	}

	/**
	 * 取得entityClass.
	 * <p/>
	 * JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * 分页查询.
	 */
	public Page pagedQuery(int pageNo, int pageSize) {
		return pagedQuery(getEntityClass(), null, pageNo, pageSize);
	}

	/**
	 * 分页查询.
	 */
	@SuppressWarnings("unchecked")
	public Page pagedQuery(Map map, int pageNo, int pageSize) {
		return pagedQuery(getEntityClass(), map, pageNo, pageSize);
	}

	public Page pagedQuery(Map map, int pageNo, int pageSize, int totalRow) {
		return pagedQuery(getEntityClass(), map, pageNo, pageSize, totalRow);
	}

	/**
	 * 根据ID移除对象.
	 */
	public int removeById(Integer id) {
		return removeById(getEntityClass(), id);
	}

	/**
	 * 根据ID移除对象.
	 */
	public int removeById(String id) {
		return removeById(getEntityClass(), id);
	}

	/**
	 * Map查询
	 * 
	 * @param map
	 *            包含各种属性的查询
	 */
	public List<T> find(Map map) {
		return super.find(getEntityClass(), map);
	}

	/**
	 * 对象查询，如果是map类型自动加上findBy.
	 * 
	 * @param o
	 *            包含各种属性的查询
	 */
	public List<T> find(Object o) {
		Map map = new HashMap();
		try {
			map = BeanUtils.describe(o);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		return super.find(getEntityClass(), map);
	}

	/**
	 * 保存对象. 为了实现IEntityDao 我在内部使用了insert和upate 2个方法.
	 */
	public void save(Object o) {
		super.insert(o);
	}

	public int delete(Object o) {
		return super.removeById(o);
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public Integer getCountBy(String name, Object value) {
		return super.getCountBy(getEntityClass(), name, value);
	}

	public Integer getCount() {
		return super.getCount(getEntityClass());
	}

	public Integer getCount(Map map) {
		return super.getCount(getEntityClass(), map);
	}
}
