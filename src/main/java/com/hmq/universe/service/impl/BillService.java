package com.hmq.universe.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmq.universe.dao.IBillDao;
import com.hmq.universe.model.po.Bill;
import com.hmq.universe.model.po.User;
import com.hmq.universe.model.vo.BillVO;
import com.hmq.universe.service.IBillService;
import com.hmq.universe.service.IUserService;
import com.hmq.universe.utis.DataRefUtil;

@Service
@Transactional
public class BillService extends GenViewService<BillVO,Bill, String, IBillDao> implements IBillService {
	@Autowired
	IUserService userService;
	
	@Override
	protected void refVO(List<BillVO> voList){
		DataRefUtil<BillVO,User> df=DataRefUtil.newInstance();
		df.addRef(BillVO::getCreater, User::getId);
		df.addDRef(User::getUserName, BillVO::setCreaterName);    
		df.ref(voList, userService);
	}
}
