package com.hmq.universe.utis.query;

import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtils {

	private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

	public static <T> String convertToFieldName(IGetter<T> fn) {
		SerializedLambda lambda = getSerializedLambda(fn);
		String getMethodName = lambda.getImplMethodName();
		if (getMethodName.startsWith("get")) {
			getMethodName = getMethodName.substring(3);
		} else if (getMethodName.startsWith("is")) {
			getMethodName = getMethodName.substring(2);
		}
		return getMethodName.substring(0, 1).toLowerCase() + getMethodName.substring(1);
	}

	public static <T> SerializedLambda getSerializedLambda(IGetter<T> fn) {
		Class<?> clazz = fn.getClass();
		return Optional.ofNullable(FUNC_CACHE.get(clazz)).map(WeakReference::get).orElseGet(() -> {
			SerializedLambda lambda = null;
			try {
				Method method = fn.getClass().getDeclaredMethod("writeReplace");
				method.setAccessible(Boolean.TRUE);
				lambda = (SerializedLambda) method.invoke(fn);
				FUNC_CACHE.put(fn.getClass(), new WeakReference<>(lambda));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return lambda;
		});
	}
}
