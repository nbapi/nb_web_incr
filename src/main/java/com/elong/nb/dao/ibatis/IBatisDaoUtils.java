package com.elong.nb.dao.ibatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * General iBATIS Utilities class with rules for primary keys and query names.
 * 
 * @author Bobby Diaz, Bryan Noll
 */
public class IBatisDaoUtils {
	
	protected static final Log log = LogFactory.getLog(IBatisDaoUtils.class);

	/**
	 * @return Returns the select query name.
	 * @param className
	 *            the name of the class - returns "get" + className + "s"
	 */
	public static String getSelectQuery(String className) {
		return "get" + className + "s";
	}

	/**
	 * @return Returns the find query name.
	 * @param className
	 *            the name of the class - returns "get" + className
	 */
	public static String getFindQuery(String className) {
		return "get" + className;
	}

	/**
	 * @param className
	 *            the name of the class - returns "selectByMap" + className
	 * @return Returns the selectByMap query name.
	 */
	public static String getSelectByMapQuery(String className) {
		return "selectByMap" + className;
	}

	/**
	 * @param className
	 *            the name of the class - returns "selectBySql" + className
	 * @return Returns the selectByMap query name.
	 */
	public static String getSelectBySqlQuery(String className) {
		return "selectBySql" + className;
	}

	/**
	 * @return Returns the insert query name.
	 * @param className
	 *            the name of the class - returns "add" + className
	 */
	public static String getInsertQuery(String className) {
		return "add" + className;
	}

	/**
	 * @return Returns the insert query name.
	 * @param className
	 *            the name of the class - returns "addByMap" + className
	 */
	public static String getInsertByMapQuery(String className) {
		return "addByMap" + className;
	}

	/**
	 * @return Returns the update query name.
	 * @param className
	 *            the name of the class - returns "update" + className
	 */
	public static String getUpdateQuery(String className) {
		return "update" + className;
	}

	/**
	 * @return Returns the update query name.
	 * @param className
	 *            the name of the class - returns "updateByMap" + className
	 */
	public static String getUpdateByMapQuery(String className) {
		return "updateByMap" + className;
	}

	/**
	 * @return Returns the delete query name.
	 * @param className
	 *            the name of the class - returns "delete" + className
	 */
	public static String getDeleteQuery(String className) {
		return "delete" + className;
	}

	/**
	 * @param className
	 *            the name of the class - returns "count" + className
	 * @return Returns the count query name.
	 */
	public static String getCountQuery(String className) {
		return "count" + className;
	}
}
