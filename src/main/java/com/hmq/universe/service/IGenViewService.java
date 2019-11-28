package com.hmq.universe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.hmq.universe.model.po.IDModel;

public interface IGenViewService<VO, PO extends IDModel<ID>, ID extends Serializable> extends IGenService<PO, ID> {

	public VO getVOById(ID id);

	public VO saveOneVO(VO vo);
	
	public List<VO> saveAllVO(List<VO> voList);

	public List<VO> findVOByFilter(Map<String, Object> filter);

	public List<VO> findVOByFilter(Map<String, Object> filter, String orderBy, String order);

	public List<VO> findVOByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order);

	public Page<VO> findVOByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize,
			String orderBy, String order);
}
