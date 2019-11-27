package com.hmq.universe.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.hmq.universe.dao.IBillDao;
import com.hmq.universe.model.po.Bill;
import com.hmq.universe.model.po.User;
import com.hmq.universe.model.vo.BillVO;
import com.hmq.universe.service.IBillService;
import com.hmq.universe.service.IUserService;
import com.hmq.universe.utis.DataRefUtil;

@Service
@Transactional
public class BillService extends GeneralVService<BillVO,Bill, String, IBillDao> implements IBillService {
	@Autowired
	IUserService userService;
	
	@Override
	public List<BillVO> findByFilter2(Map<String, Object> filter) {
		List<Bill> billList= this.findByFilter(filter, null, null, null, null);
		List<BillVO> billVOList=this.toVO(billList, BillVO.class);
				//JSON.parseArray(JSON.toJSONString(billList), BillVO.class);
		
		DataRefUtil<BillVO,User> df=DataRefUtil.newInstance();
		df.addRef(BillVO::getCreater, User::getId);
		df.addDRef(User::getUserName, BillVO::setCreaterName);    
		df.ref(billVOList, userService);
		
		return billVOList;
	}
}
