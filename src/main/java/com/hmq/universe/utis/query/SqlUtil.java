package com.hmq.universe.utis.query;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.SQLQuery;

public class SqlUtil {
	

	/**
	 * 给sql 参数设置值
	 * @param query
	 * @param params
	 */
	 @SuppressWarnings("rawtypes")
	public static void setParameters(final SQLQuery query,final Map<String,Object>params) {
		  for(Map.Entry<String,Object>entry:params.entrySet()) {
			  if(entry.getValue() instanceof  java.util.Collection) {
				  query.setParameterList(entry.getKey(), (Collection) entry.getValue());
			  }else {
				  query.setParameter(entry.getKey(),entry.getValue());
			  }
		  } 
	   	 
	}
    /**
	 * 给sql 参数设置值
	 * @author 
	 * @param query
	 * @param params
	 */
	 public static void setParameters(final Query query,final Map<String,Object>params) {
		  for(Map.Entry<String,Object>entry:params.entrySet()) { 
				  query.setParameter(entry.getKey(),entry.getValue()); 
		  }  
	}
}
