package com.hmq.universe.controller.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hmq.universe.model.po.IDModel;
import com.hmq.universe.service.IGenViewService;

public class GenViewController<VO extends IDModel<ID>, PO extends IDModel<ID>, ID extends Serializable, Service extends IGenViewService<VO, PO, ID>>
		extends GenController<PO,ID,Service> {
	@Autowired
	private Service service;

	@GetMapping("/getVO/{id}")
	public VO getVOById(@PathVariable ID id) {
		VO vo = this.service.getVOById(id);
		return vo;
	}

	@PostMapping("/saveOneVO")
	public ID saveOneVO(@RequestBody VO vo) {
		return this.service.saveOneVO(vo).getId();
	}

	@PostMapping("/saveAllVO")
	public int saveAllVO(@RequestBody List<VO> voList) {
		return this.service.saveAllVO(voList).size();
	}

	@GetMapping("/searchVO")
	public List<VO> searchVO(HttpServletRequest request, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		Map<String, Object> filter = getParams(request);
		List<VO> voList = this.service.findVOByFilter(filter, pageIndex, pageSize, orderBy, order);
		return voList;
	}

	@GetMapping("/serachVOWithPage")
	public Page<VO> serachVOWithPage(HttpServletRequest request, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		Map<String, Object> filter = getParams(request);
		Page<VO> voPage = this.service.findVOByFilterWithPage(filter, pageIndex, pageSize, orderBy, order);
		return voPage;
	}

}
