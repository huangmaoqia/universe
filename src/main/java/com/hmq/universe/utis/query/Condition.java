package com.hmq.universe.utis.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.hmq.universe.utis.DateUtil;


public class Condition<T> implements Specification<T> {
	private static final long serialVersionUID = 1L;
	private String fieldName;
	private IGetter iGetter;
	private EComparisonOperator op;
	private Object value;
	
	public enum EComparisonOperator {
		EQ, NE, LIKE, GT, LT, GTE, LTE, IN
	}
	
	public Condition(String fieldName,EComparisonOperator op,Object value){
		this.fieldName=fieldName;
		this.op=op;
		this.value=value;
	}
	
	public Condition(String fieldName,Object value){
		this.fieldName=fieldName;
		this.value=value;
	}
	
	public Condition(IGetter iGetter,EComparisonOperator op,Object value){
		this.iGetter=iGetter;
		this.op=op;
		this.value=value;
	}
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder){
		if(fieldName==null || "".equals(fieldName)){
			this.fieldName=BeanUtils.convertToFieldName(this.iGetter);
		}
		
        if(op==null){
			int index=fieldName.lastIndexOf("-");
			if(index!=-1){
				String opStr=fieldName.substring(index+1);
				op=EComparisonOperator.valueOf(opStr);
				fieldName=fieldName.substring(0, index);
			}else{
				op=EComparisonOperator.EQ;
			}
        }
        Path expression=root.get(fieldName);
        Class clazz=expression.getJavaType();
        if(op==EComparisonOperator.IN){
        	if(value instanceof String){
        		String[] strArr=((String) value).split(",");
        		value=Arrays.asList(strArr);
        	}
        }
        
        if(clazz==Date.class){
        	if(value instanceof String){
        		this.value=DateUtil.toDate(value.toString());
        	}else if(value instanceof Collection){
        		Set<Date> set=new HashSet<Date>();
        		Collection<String> collection=(Collection<String>)value;
        		for(String str:collection){
        			set.add(DateUtil.toDate(str));
        		}
        		value=set;
        	}
        }
        switch (op) {  
        case EQ:  
            return builder.equal(expression, value);  
        case NE:  
            return builder.notEqual(expression, value);  
        case LIKE:  
            return builder.like(expression, "%" + value + "%");  
        case LT:  
            return builder.lessThan(expression, (Comparable) value);  
        case GT:  
            return builder.greaterThan(expression, (Comparable) value);  
        case LTE:  
            return builder.lessThanOrEqualTo(expression, (Comparable) value);  
        case GTE:  
            return builder.greaterThanOrEqualTo(expression, (Comparable) value);  
        case IN:
//        	Collection values=(Collection)value;
//            Predicate[] predicates = new Predicate[values.size()];  
//            int i=0;  
//            for(Object obj : values){  
//            	predicates[i]=builder.equal(expression, obj);  
//                i++;  
//            }  
//            return builder.or(predicates);
        	In in = builder.in(expression);
            Collection values=(Collection)value;
            if(values.size()>0){
	            for(Object obj : values){ 
	            	in.value(obj);
	            }
	            return in;
            }else{
            	return builder.notEqual(expression, expression); 
            }
        default:  
        	return null;
        }  
    }

	
}
