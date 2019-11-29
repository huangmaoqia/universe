package com.hmq.universe.controller.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmq.universe.model.po.BillDetail;
import com.hmq.universe.model.vo.BillDetailVO;
import com.hmq.universe.service.IBillDetailService;

@RestController
@RequestMapping("/billDetail")
public class BillDetailController extends GenViewController<BillDetailVO, BillDetail, String, IBillDetailService> {
}
