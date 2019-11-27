package com.hmq.universe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.hmq.universe.model.po.CommonModel;

public interface IGeneralService<M extends CommonModel<ID>, ID extends Serializable> {

	public M getById(ID id);

	public void deleteById(ID id);

	public M saveOne(M entity);

	public List<M> saveAll(List<M> entities);
	
	public long countBySpecification(Specification<M> spec);

	public List<M> findBySpecification(Specification<M> spec);

	public List<M> findBySpecification(Specification<M> spec, String orderBy, String order);

	public List<M> findBySpecification(Specification<M> spec, Integer pageIndex, Integer pageSize, String orderBy,
			String order);

	public Page<M> findBySpecificationWithPage(Specification<M> spec, Integer pageIndex, Integer pageSize, String orderBy,
			String order);

	public long countByFilter(Map<String, Object> filter);
	
	public List<M> findByFilter(Map<String, Object> filter);

	public List<M> findByFilter(Map<String, Object> filter, String orderBy, String order);

	public List<M> findByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order);

	public Page<M> findByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order);
	
}
