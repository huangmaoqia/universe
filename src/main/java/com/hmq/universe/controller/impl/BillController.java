package com.hmq.universe.controller.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmq.universe.model.po.Bill;
import com.hmq.universe.model.vo.BillVO;
import com.hmq.universe.service.IBillService;

@RestController
@RequestMapping("/bill")
public class BillController extends GenViewController<BillVO, Bill, String, IBillService> {
}
