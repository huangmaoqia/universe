package com.hmq.universe.utis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hmq.universe.model.po.User;
import com.hmq.universe.service.IGeneralService;
import com.hmq.universe.utis.query.Expression;
import com.hmq.universe.utis.query.IGetter;
import com.hmq.universe.utis.query.ISetter;

public class DataRefUtil<S, T> {
	
	private DataRefUtil() {
	}

	public static <S, T> DataRefUtil<S, T> newInstance() {
		return new DataRefUtil<S, T>();
	}

	private Map<IGetter<S>, IGetter<T>> map = new HashMap<>();
	
	private Map<IGetter<T>, ISetter<S,Object>> dmap = new HashMap<>();

	public DataRefUtil<S, T> addRef(IGetter<S> sGetter, IGetter<T> tGetter) {
		map.put(sGetter, tGetter);
		return this;
	}
	
	public <V> DataRefUtil<S, T> addDRef(IGetter<T> tGetter,ISetter<S,V> sSetter) {
		dmap.put(tGetter,(ISetter<S, Object>) sSetter);
		return this;
	}

	public <V,TS extends IGeneralService> void ref(List<S> sourceList, TS targetService) {
		Expression<T> exp = new Expression<>();
		
		Set<IGetter<S>> sGetterSet = map.keySet();
		for (IGetter<S> sGetter : sGetterSet) {
			List<Object> valueList=new ArrayList<>();
			for (S source : sourceList) {
				Object value = sGetter.apply(source);
				valueList.add(value);
			}
			exp.addCdIn(map.get(sGetter), valueList);
		}
		List<T> tList = targetService.findBySpecification(exp);
		Map<String,T> key2TMap=new HashMap<String,T>();
		for (T obj : tList) {
			String key="";
			for (Object getter : sGetterSet) {
				IGetter<T> tGetter = (IGetter<T>) map.get(getter);
				key+=String.valueOf(tGetter.apply(obj).hashCode());
			}
			key2TMap.put(key, obj);
		}
		
		Set<IGetter<T>> dsGetterSet = dmap.keySet();
		for (S source : sourceList) {
			String key="";
			for (IGetter<S> sGetter : sGetterSet) {
				key+=String.valueOf(sGetter.apply(source).hashCode());
			}
			T target=key2TMap.get(key);
			for (IGetter<T> tGetter : dsGetterSet) {
				Object tValue=tGetter.apply(target);
				ISetter<S, Object> sSetter = dmap.get(tGetter);
				sSetter.apply(source, tValue);
			}
		}
	}

	public static void main(String[] args) {
		User u = new User();
		f2(User::setId, u, "234");
		f1(User::getId, u);
	}

	public static <K> void f1(IGetter<K> getter, K u) {
		Object id = getter.apply(u);
		System.out.println(id.toString());

	}
	
	public static <K,V> void f2(ISetter<K,V> setter, K k ,V value) {
		setter.apply(k,value);

	}
}
