package com.hmq.universe.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmq.universe.dao.IBillDao;
import com.hmq.universe.model.po.Bill;
import com.hmq.universe.model.po.User;
import com.hmq.universe.model.vo.BillDetailVO;
import com.hmq.universe.model.vo.BillVO;
import com.hmq.universe.service.IBillDetailService;
import com.hmq.universe.service.IBillService;
import com.hmq.universe.service.IUserService;
import com.hmq.universe.utis.DataRelation;

@Service
@Transactional
public class BillService extends GenViewService<BillVO, Bill, String, IBillDao>
		implements IBillService, InitializingBean {
	@Autowired
	IUserService userService;

	@Autowired
	IBillDetailService billDetailService;

	@Override
	public void afterPropertiesSet() throws Exception {
		DataRelation<BillVO, User, IUserService> cdr = DataRelation.newInstance();
		cdr.setTargetService(userService);
		cdr.addForwardRelation(BillVO::getCreater, User::getId);
		cdr.addBackwardRelation(User::getUserName, BillVO::setCreaterName);
		this.addColumnDataRelation(cdr);

		DataRelation<BillVO, BillDetailVO, IBillDetailService> sdr = DataRelation.newInstance();
		sdr.setTargetService(billDetailService);
		sdr.addForwardRelation(BillVO::getId, BillDetailVO::getBillId);
		sdr.addBackwardRelation(null, BillVO::setBillDetailVOList);
		this.addSonDataRelation(sdr);
	}
}
