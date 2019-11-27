package com.hmq.universe.controller.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmq.universe.model.po.Bill;
import com.hmq.universe.model.vo.BillVO;
import com.hmq.universe.service.IBillService;

@RestController
@RequestMapping("/bill")
public class BillController extends GeneralController<BillVO, Bill, String, IBillService> {
	@GetMapping("/search2")
	public List<BillVO> serach2(HttpServletRequest request, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		Map<String, Object> filter = getParams(request);
		return this.getService().findByFilter2(filter);
	}
}
