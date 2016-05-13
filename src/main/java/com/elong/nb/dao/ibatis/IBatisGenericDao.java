package com.elong.nb.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.elong.nb.dao.ibatis.model.Page;

/**
 * IBatis Dao的泛型基类. <p/>
 * 继承于Spring的SqlMapClientDaoSupport,提供分页函数和若干便捷查询方法，并对返回值作了泛型类型转换.
 * 
 * @author suwei
 * @param <T>
 * @see SqlMapClientDaoSupport
 */
public class IBatisGenericDao{
	@Resource(name="SqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate;
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	/**
	 * 获得主键
	 * <p>功能描述:[方法功能中文描述]</p>
	 * @return
	 * @author:wanghao
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public String getGeneratedKey(){
		return sqlSessionTemplate.selectOne("getGeneratedKey");
	}
	/**
	 * 根据ID获取对象
	 */
	public <T> T get(Class<T> clazz, Object id) {
		T o = (T) sqlSessionTemplate.selectOne(IBatisDaoUtils.getFindQuery(ClassUtils.getShortName(clazz)),id);
		return o;
	}
	/**
	 * 获取全部对象
	 */
	public <T> List<T> getAll(Class<T> clazz) {
		return sqlSessionTemplate.selectList(IBatisDaoUtils.getSelectQuery(ClassUtils.getShortName(clazz)),null);
	}
	public <T> List<T> getAll(Class<T> clazz, Map map) {
		return sqlSessionTemplate.selectList(IBatisDaoUtils.getSelectQuery(ClassUtils.getShortName(clazz)),map);
	}
	public <T> T get(String selectName,Object obj){
		return sqlSessionTemplate.selectOne(selectName, obj);
	}

	/**
	 * 新增对象
	 */
	public <T> Object insert(Object o) {
		return insert(o.getClass(), o);
	}

	/**
	 * 新增对象
	 */
	public <T> Object insert(Class<T> clazz, Object o) {
		return sqlSessionTemplate.insert(IBatisDaoUtils.getInsertQuery(ClassUtils.getShortName(clazz)), o);
	}

	/**
	 * 保存对象
	 */
	public int update(Object o) {
		return update(o.getClass(), o);
	}
	/**
	 * 根据ID删除对象
	 */
	public <T> int removeById(Object o) {
		return removeById(o.getClass(), o);
	}

	/**
	 * 保存对象
	 */
	public <T> int update(Class<T> clazz, Object o) {
		return sqlSessionTemplate.update(IBatisDaoUtils.getUpdateQuery(ClassUtils.getShortName(clazz)), o);
	}

	/**
	 * 根据ID删除对象
	 */
	public <T> int removeById(Class<T> clazz, Object id) {
		return sqlSessionTemplate.delete(IBatisDaoUtils.getDeleteQuery(ClassUtils.getShortName(clazz)), id);
	}

	/**
	 * 对象查询，如果是map类型自动加上findBy.
	 * 
	 * @param o 包含各种属性的查询
	 */
	public <T> List<T> find(Class<T> clazz, Map map) {
		return sqlSessionTemplate.selectList(IBatisDaoUtils.getSelectByMapQuery(ClassUtils.getShortName(clazz)), map);
	}
	public <T> T sum(Class<T> clazz, Map map) {
		return sqlSessionTemplate.selectOne("sum"+ClassUtils.getShortName(clazz), map);
	}
	/**
	 * 对于不是正规Class的后台查询
	 * @param <T>
	 * @param clazzClone
	 * @param map
	 * @return
	 */
	public <T> List<T> find(String clazzClone, Map map) {
		return sqlSessionTemplate.selectList(IBatisDaoUtils.getSelectByMapQuery(clazzClone), map);
	}
	public <T> List<T> find(String clazzClone, Object bean) {
		return sqlSessionTemplate.selectList(IBatisDaoUtils.getSelectByMapQuery(clazzClone), bean);
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> findBy(Class<T> clazz, String name, Object value) {
		Assert.hasText(name);
		Map map = new HashMap();
		map.put(name, value);
		return find(clazz, map);
	}

	/**
	 * 取总记录数
	 */
	public Integer getCountBy(Class clazz, String name, Object value) {
		Map map = new HashMap();
		map.put(name, value);
		return getCount(clazz, map);
	}
	public Integer getCount(Class clazz) {
		return (Integer) sqlSessionTemplate.selectOne(IBatisDaoUtils.getCountQuery(ClassUtils.getShortName(clazz)));
	}
	public Integer getCount(Class clazz, Map map) {
		return (Integer) sqlSessionTemplate.selectOne(IBatisDaoUtils.getCountQuery(ClassUtils.getShortName(clazz)), map);
	}
	public Integer getCount(String clazzClone, Map map) {
		return (Integer) sqlSessionTemplate.selectOne(IBatisDaoUtils.getCountQuery(clazzClone), map);
	}
	/**
	 * 分页查询函数，使用PaginatedList.
	 * 
	 * @param pageNo页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Class clazz, Map map, int pageNo, int pageSize) {
		if (pageNo == 0) pageNo ++;
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

		// 计算总数
		Integer totalCount = getCount(clazz, map);
		return pagedQuery(clazz, map, pageNo, pageSize, totalCount);
	}

	public Page pagedQuery(Class clazz, Map map, int pageNo, int pageSize, int totalCount) {
		if (pageNo == 0) pageNo ++;

		// 如果没有数据则返回Empty Page
		Assert.notNull(totalCount, "totalCount Error");

		if (totalCount == 0) 
			return new Page();

		List list;
		int totalPageCount = 0;
		int startIndex = 0;

		// 如果pageSize小于0,则返回所有数据,等同于getAll
		if (pageSize > 0) {

			// 计算页数
			totalPageCount = (totalCount / pageSize);
			totalPageCount += ((totalCount % pageSize) > 0) ? 1 : 0;
			
			// 计算skip数量
			if(pageNo == -1)
				pageNo = totalPageCount;
			if (totalPageCount > pageNo)
				startIndex = (pageNo - 1) * pageSize;
			else
				startIndex = (totalPageCount - 1) * pageSize;
			if (map == null)
				map = new HashMap(2);
			map.put("startIndex", startIndex);
			map.put("endIndex", startIndex+pageSize);
			map.put("pageSize", pageSize);
			list = find(clazz, map);
		} else {
			list = find(clazz, map);
		}
		return new Page(startIndex, totalCount, pageSize, list);
	}
	public Page pagedQuery(String clazzClone, Map map, int pageNo, int pageSize, int totalCount) {
		if (pageNo == 0) pageNo ++;

		// 如果没有数据则返回Empty Page
		Assert.notNull(totalCount, "totalCount Error");

		if (totalCount == 0) {
			return new Page();
		}

		List list;
		int totalPageCount = 0;
		int startIndex = 0;

		// 如果pageSize小于0,则返回所有数据,等同于getAll
		if (pageSize > 0) {
			// 计算页数
			totalPageCount = (totalCount / pageSize);
			totalPageCount += ((totalCount % pageSize) > 0) ? 1 : 0;
			if(pageNo == -1)
				startIndex = (totalPageCount - 1) * pageSize;
			// 计算skip数量
			if(pageNo == -1)
				pageNo = totalPageCount;
			if (totalPageCount > pageNo)
				startIndex = (pageNo - 1) * pageSize;
			else
				startIndex = (totalPageCount - 1) * pageSize;
			if (map == null)
				map = new HashMap(2);
			map.put("startIndex", startIndex);
//			map.put("endIndex", startIndex+pageSize);
			map.put("pageSize", pageSize);
			list = find(clazzClone, map);
		} else {
			list = find(clazzClone, map);
		}
		return new Page(startIndex, totalCount, pageSize, list);
	}
}
