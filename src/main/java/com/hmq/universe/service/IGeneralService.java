package com.hmq.universe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.hmq.universe.model.po.CommonModel;

public interface IGeneralService<Model extends CommonModel<ID>, ID extends Serializable>{
	
	public Model getById(ID id);
	
	public void deleteById(ID id);
	
	public Model saveOne(Model entity);
	
	public List<Model> saveAll(List<Model> entities);
	
	public List<Model> findBySpecification(Specification<Model> spec);
	
	public List<Model> findBySpecification(Specification<Model> spec, String sort, String dsc);
	
	public List<Model> findBySpecification(Specification<Model> spec, int page, int pageSize, String sort, String dsc);
	
	public Page<Model> findBySpecificationWithPage(Specification<Model> spec, int page, int pageSize, String sort, String dsc);
	
	public List<Model> findByFilter(Map<String, Object> filter);
	
	public List<Model> findByFilter(Map<String, Object> filter, String sort, String dsc);
	
	public List<Model> findByFilter(Map<String, Object> filter, int page, int pageSize, String sort, String dsc);
	
	public Page<Model> findByFilterWithPage(Map<String, Object> filter, int page, int pageSize, String sort, String dsc);
}
