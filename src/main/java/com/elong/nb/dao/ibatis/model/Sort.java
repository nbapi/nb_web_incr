package com.elong.nb.dao.ibatis.model;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Jeff Johnston
 */
public final class Sort {
    private String alias;
    private String property;
    private String sortOrder;

    public Sort() {
        this.alias = null;
        this.property = null;
        this.sortOrder = null;
    }

    public Sort(String alias, String property, String sortOrder) {
        this.alias = alias;
        this.property = property;
        this.sortOrder = sortOrder;
    }

	public String getAlias() {
        return alias;
    }

    public String getProperty() {
        return property;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Map getSortValueMap(){
		String sortProperty = getProperty();
		String sortOrder = getSortOrder();
		
		Map sortValueMap = new HashMap();
		if (sortProperty!=null){
			sortValueMap.put(sortProperty, sortOrder);
		}
		return sortValueMap;
		
	}
    
    public boolean isSorted() {
        return sortOrder != null;
    }
    
    public boolean isAliased() {
        return !alias.equals(property);
    }
    
}