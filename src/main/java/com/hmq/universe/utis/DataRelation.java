package com.hmq.universe.utis;

import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hmq.universe.model.IVO;
import com.hmq.universe.model.vo.UserVO;
import com.hmq.universe.service.IGenService;
import com.hmq.universe.service.IGenViewService;
import com.hmq.universe.utis.query.BeanUtils;
import com.hmq.universe.utis.query.Expression;
import com.hmq.universe.utis.query.IGetter;
import com.hmq.universe.utis.query.ISetter;

public class DataRelation<S, T ,TS extends IGenService> {
	
	private DataRelation() {
	}

	public static <S, T ,TS extends IGenService> DataRelation<S, T ,TS> newInstance() {
		return new DataRelation();
	}
	
	private TS targetService=null;

	private Map<IGetter<S>, IGetter<T>> forwardRelation = new HashMap<>();
	
	private Map<IGetter<T>, ISetter<S,Object>> backwardRelation = new HashMap<>();
	
	public DataRelation<S, T,TS> setTargetService(TS targetService){
		this.targetService=targetService;
		return this;
	}

	public DataRelation<S, T,TS> addForwardRelation(IGetter<S> sGetter, IGetter<T> tGetter) {
		forwardRelation.put(sGetter, tGetter);
		return this;
	}
	
	public <V> DataRelation<S, T,TS> addBackwardRelation(IGetter<T> tGetter,ISetter<S,V> sSetter) {
		backwardRelation.put(tGetter,(ISetter<S, Object>) sSetter);
		return this;
	}

	public void relate(List<S> sourceList) {
		Expression<T> exp = new Expression<>();
		
		Set<IGetter<S>> sgSet = forwardRelation.keySet();
		
		IGetter<T> oneTg=null;
		for (IGetter<S> sg : sgSet) {
			List<Object> valueList=new ArrayList<>();
			for (S source : sourceList) {
				Object value = sg.apply(source);
				valueList.add(value);
			}
			exp.addCdIn(forwardRelation.get(sg), valueList);
			oneTg=forwardRelation.get(sg);
		}
		SerializedLambda oneTgLambda =BeanUtils.getSerializedLambda(oneTg);
		String instantiatedMethodType=oneTgLambda.getInstantiatedMethodType();
		//(Lcom/hmq/universe/model/po/User;)Ljava/lang/Object;
		
		String oneTgClassName=instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(";"));
		oneTgClassName=oneTgClassName.replace("/", ".");
		
		Class<T> oneTgClass=null;
		try {
			oneTgClass=(Class<T>) this.getClass().getClassLoader().loadClass(oneTgClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<T> tList=null;
		Class<?>[] interfaces=oneTgClass.getInterfaces();
		if(Arrays.asList(interfaces).contains(IVO.class)){
			tList=((IGenViewService)targetService).findVOBySpec(exp);
		}else{
			tList=targetService.findBySpec(exp);
		}
		if(tList!=null && tList.size()>0){
			Map<String,List<T>> key2TMap=new HashMap<>();
			for (T t : tList) {
				String key="";
				for (IGetter<S> sg : sgSet) {
					IGetter<T> tGetter = (IGetter<T>) forwardRelation.get(sg);
					key+=String.valueOf(tGetter.apply(t).hashCode());
				}
				if(key2TMap.get(key)==null){
					key2TMap.put(key, new ArrayList<T>());
				}
				key2TMap.get(key).add(t);
			}
			
			Set<IGetter<T>> tgSet = backwardRelation.keySet();
			for (S source : sourceList) {
				String key="";
				for (IGetter<S> sg : sgSet) {
					key+=String.valueOf(sg.apply(source).hashCode());
				}
				List<T> ktList=key2TMap.get(key);
				if(ktList!=null && ktList.size()>0){
					for (IGetter<T> tg : tgSet) {
						ISetter<S, Object> ts = backwardRelation.get(tg);
						Object tValue=null;
						if(tg==null){
							tValue=ktList;
						}else{
							tValue=tg.apply(ktList.get(0));
						}
						ts.apply(source, tValue);
					}
				}
				
			}
		}
	}

	public static void main(String[] args) {
//		User u = new User();
//		f2(User::setId, u, "234");
//		f1(User::getId, u);
		
		Class<?>[] interfaces=UserVO.class.getInterfaces();
		System.out.println(Arrays.asList(interfaces).contains(IVO.class));
	}

	public static <K> void f1(IGetter<K> getter, K u) {
		Object id = getter.apply(u);
		System.out.println(id.toString());

	}
	
	public static <K,V> void f2(ISetter<K,V> setter, K k ,V value) {
		setter.apply(k,value);

	}

	public Map<IGetter<S>, IGetter<T>> getForwardRelation() {
		return forwardRelation;
	}

	public Map<IGetter<T>, ISetter<S, Object>> getBackwardRelation() {
		return backwardRelation;
	}

	public TS getTargetService() {
		return targetService;
	}
}
