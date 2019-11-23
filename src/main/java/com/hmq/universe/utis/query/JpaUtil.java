package com.hmq.universe.utis.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public final class JpaUtil{
	
	public static Sort buildSort(String sort, String dsc) {
		if(dsc==null){
			dsc="asc";
		}
		String[] sortArr = sort.split(",");
		String[] dscArr = dsc.split(",");
		List<Order> orderList = new ArrayList<Order>();
		if (sortArr.length != dscArr.length) {
			throw new RuntimeException("buildPageable.sort.dsc,sort.length<>dsc.length");
		}else{
			for (int i = 0; i < sortArr.length; ++i) {
				Order order = new Order("asc".equalsIgnoreCase(dscArr[i]) ? Direction.ASC : Direction.DESC, sortArr[i]);
				orderList.add(order);
			}
		}
		Sort pageSort = Sort.by(orderList);
		return pageSort;
	}
	
	public static Pageable buildPageable(int page, int pageSize, String sort, String dsc) {
		Pageable pageable = null;
		if(sort!=null){
			Sort pageSort=buildSort(sort, dsc);
			pageable = new PageRequest(page, pageSize, pageSort);
		} else {
			pageable = new PageRequest(page, pageSize);
		}
		return pageable;
	}
}
