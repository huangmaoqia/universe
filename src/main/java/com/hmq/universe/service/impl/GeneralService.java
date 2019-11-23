package com.hmq.universe.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.hmq.universe.dao.IGeneralDao;
import com.hmq.universe.model.po.CommonModel;
import com.hmq.universe.model.vo.ITokenVO;
import com.hmq.universe.service.IGeneralService;
import com.hmq.universe.utis.TokenUtil;
import com.hmq.universe.utis.UUIDUtil;
import com.hmq.universe.utis.query.ExpressionUtil;
import com.hmq.universe.utis.query.JpaUtil;

public class GeneralService<Model extends CommonModel<ID>, ID extends Serializable,Dao extends IGeneralDao<Model, ID>> implements IGeneralService<Model, ID> {
	@Autowired
	private Dao dao;
	
	protected Dao getDao() {
		return this.dao;
	}
	
	public Model getById(ID id){
		Model model=this.dao.getOne(id);
		return model;
	}
	
	public void deleteById(ID id){
		this.dao.deleteById(id);
	}
	
	public Model saveOne(Model entity){
		handleData(entity);
		return this.dao.save(entity);
	}
	
	public List<Model> saveAll(List<Model> entities){
		handleData(entities);
		return this.dao.saveAll(entities);
	}
	
	private void handleData(Model entity){
		ITokenVO<ID> tokenVO=(ITokenVO<ID>) TokenUtil.getTokenVO();
		if(entity.getCreateTime()==null){
			entity.setCreateTime(new Date());
		}
		if(entity.getCreater()==null){
			entity.setCreater(tokenVO.getUserid());
		}
		entity.setUpdateTime(new Date());
		entity.setModifier(tokenVO.getUserid());
		
		if(entity.getId()==null){
			entity.setId((ID) UUIDUtil.newUUID());
		}
	}
	
	private void handleData(List<Model> entities){
		for(Model entity:entities){
			handleData(entity);
		}
	}

	@Override
	public List<Model> findBySpecification(Specification<Model> spec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Model> findBySpecification(Specification<Model> spec, String sort, String dsc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Model> findBySpecification(Specification<Model> spec, int page, int pageSize, String sort, String dsc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Model> findBySpecificationWithPage(Specification<Model> spec, int page, int pageSize, String sort,
			String dsc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Model> findByFilter(Map<String, Object> filter) {
		List<Model> modelList=null;
		if (filter != null && filter.size() >= 1) {
			Specification<Model> specs = ExpressionUtil.genExpressionByFilter(filter);
			modelList = this.getDao().findAll(specs);
		}else{
			modelList= this.getDao().findAll();
		}
		return modelList;
	}

	@Override
	public List<Model> findByFilter(Map<String, Object> filter, String sort, String dsc) {
		List<Model> modelList=null;
		Sort pageSort=JpaUtil.buildSort(sort, dsc);
		if (filter != null && filter.size() >= 1) {
			Specification<Model> specs = ExpressionUtil.genExpressionByFilter(filter);
			modelList = this.getDao().findAll(specs,pageSort);
		}else{
			modelList= this.getDao().findAll(pageSort);
		}
		return modelList;
	}
	
	public List<Model> findByFilter(Map<String, Object> filter, int page, int pageSize, String sort, String dsc) {
		Page<Model> pageData=this.findByFilterWithPage(filter, page, pageSize, sort, dsc);
		return pageData.getContent();
	}

	@Override
	public Page<Model> findByFilterWithPage(Map<String, Object> filter, int page, int pageSize, String sort,
			String dsc) {
		Pageable pageable = JpaUtil.buildPageable(page, pageSize, sort, dsc);
		Page<Model> pageData = null;
		if (filter != null && filter.size() >= 1) {
			Specification<Model> specs = ExpressionUtil.genExpressionByFilter(filter);
			pageData = this.getDao().findAll(specs, pageable);
		} else {
			pageData = this.getDao().findAll(pageable);
		}
		return pageData;
	}
}
