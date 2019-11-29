package com.hmq.universe.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmq.universe.dao.IBillDetailDao;
import com.hmq.universe.model.po.BillDetail;
import com.hmq.universe.model.vo.BillDetailVO;
import com.hmq.universe.service.IBillDetailService;

@Service
@Transactional
public class BillDetailService extends GenViewService<BillDetailVO,BillDetail, String, IBillDetailDao> implements IBillDetailService {
}
