package com.hmq.universe.utis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hmq.universe.model.po.User;
import com.hmq.universe.service.IGeneralService;
import com.hmq.universe.service.impl.GeneralService;
import com.hmq.universe.utis.query.Expression;
import com.hmq.universe.utis.query.IGetter;
import com.hmq.universe.utis.query.ISetter;

public class DataRefUtil<S, T> {
	
	private static DataRefUtil instance = new DataRefUtil<>();

	private DataRefUtil() {
	}

	public static DataRefUtil getInstance() {
		return instance;
	}

	private Map<IGetter<S>, IGetter<T>> map = new HashMap<>();
	
	private Map<IGetter<T>, ISetter> dmap = new HashMap<>();

	public DataRefUtil addRef(IGetter<S> sGetter, IGetter<T> tGetter) {
		map.put(sGetter, tGetter);
		return instance;
	}
	
	public <V> DataRefUtil<S, T> addDRef(IGetter<T> tGetter,ISetter<S,V> sSetter) {
		dmap.put(tGetter,sSetter);
		return instance;
	}

	public <S, T, TS extends IGeneralService> void ref(List<S> sourceList, TS targetService) {
		Expression<T> exp = new Expression<>();
		
		Set sGetterSet = map.keySet();
		for (Object getter : sGetterSet) {
			List<Object> valueList=new ArrayList<>();
			IGetter<S> sGetter = (IGetter<S>) getter;
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
		
		Set dsGetterSet = dmap.keySet();
		for (S source : sourceList) {
			String key="";
			for (Object getter : sGetterSet) {
				IGetter<S> sGetter = (IGetter<S>) getter;
				key+=String.valueOf(sGetter.apply(source).hashCode());
			}
			T target=key2TMap.get(key);
			for (Object getter : dsGetterSet) {
				IGetter<T> tGetter = (IGetter<T>) getter;
				Object tValue=tGetter.apply(target);
				ISetter sSetter = (ISetter) dmap.get(getter);
				sSetter.apply(source, tValue);
			}
		}
		System.out.println("wonderful");
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
