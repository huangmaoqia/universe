package com.hmq.universe.service;

import java.util.List;
import java.util.Map;

import com.hmq.universe.model.po.Bill;
import com.hmq.universe.model.vo.BillVO;

public interface IBillService extends IGeneralVService<BillVO,Bill,String>{

	List<BillVO> findByFilter2(Map<String, Object> filter);
}
