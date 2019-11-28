package com.hmq.universe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.hmq.universe.model.po.IDModel;

public interface IGenService<PO extends IDModel<ID>, ID extends Serializable> {

	public PO getById(ID id);

	public void deleteById(ID id);

	public PO saveOne(PO po);

	public List<PO> saveAll(List<PO> poList);
	
	public long countBySpec(Specification<PO> spec);

	public List<PO> findBySpec(Specification<PO> spec);

	public List<PO> findBySpec(Specification<PO> spec, String orderBy, String order);

	public List<PO> findBySpec(Specification<PO> spec, Integer pageIndex, Integer pageSize, String orderBy,
			String order);

	public Page<PO> findBySpecWithPage(Specification<PO> spec, Integer pageIndex, Integer pageSize, String orderBy,
			String order);

	public long countByFilter(Map<String, Object> filter);
	
	public List<PO> findByFilter(Map<String, Object> filter);

	public List<PO> findByFilter(Map<String, Object> filter, String orderBy, String order);

	public List<PO> findByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order);

	public Page<PO> findByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order);
	
}
