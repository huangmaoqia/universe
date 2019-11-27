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

public class GeneralService<M extends CommonModel<ID>, ID extends Serializable, Dao extends IGeneralDao<M, ID>>
		implements IGeneralService<M, ID> {

	@Autowired
	private Dao dao;

	protected Dao getDao() {
		return this.dao;
	}

	@Override
	public M getById(ID id) {
		M model = this.dao.getOne(id);
		return model;
	}

	@Override
	public void deleteById(ID id) {
		this.dao.deleteById(id);
	}

	@Override
	public M saveOne(M entity) {
		handleData(entity);
		return this.dao.save(entity);
	}

	@Override
	public List<M> saveAll(List<M> entities) {
		handleData(entities);
		return this.dao.saveAll(entities);
	}

	private void handleData(M entity) {
		ITokenVO<ID> tokenVO = (ITokenVO<ID>) TokenUtil.getTokenVO();
		if (entity.getCreateTime() == null) {
			entity.setCreateTime(new Date());
		}
		if (entity.getCreater() == null) {
			entity.setCreater(tokenVO.getUserid());
		}
		entity.setUpdateTime(new Date());
		entity.setModifier(tokenVO.getUserid());

		if (entity.getId() == null) {
			entity.setId((ID) UUIDUtil.newUUID());
		}
	}

	private void handleData(List<M> entities) {
		for (M entity : entities) {
			handleData(entity);
		}
	}

	@Override
	public long countBySpecification(Specification<M> spec) {
		return this.getDao().count(spec);
	}
	
	@Override
	public List<M> findBySpecification(Specification<M> spec) {
		return this.findBySpecification(spec, null, null, null, null);
	}

	@Override
	public List<M> findBySpecification(Specification<M> spec, String orderBy, String order) {
		return this.findBySpecification(spec, null, null, orderBy, order);
	}
	
	@Override
	public List<M> findBySpecification(Specification<M> spec, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		List<M> modelList = null;
		if (pageIndex != null) {
			Pageable pageable = JpaUtil.buildPageable(pageIndex, pageSize, orderBy, order);
			modelList = this.getDao().findAll(spec, pageable).getContent();
		} else if (orderBy != null) {
			Sort pageSort = JpaUtil.buildSort(orderBy, order);
			modelList = this.getDao().findAll(spec, pageSort);
		} else {
			modelList = this.getDao().findAll(spec);
		}
		return modelList;
	}

	@Override
	public Page<M> findBySpecificationWithPage(Specification<M> spec, Integer pageIndex, Integer pageSize,
			String orderBy, String order) {
		Pageable pageable = JpaUtil.buildPageable(pageIndex, pageSize, orderBy, order);
		Page<M> pageData = this.getDao().findAll(spec, pageable);
		return pageData;
	}

	@Override
	public long countByFilter(Map<String, Object> filter) {
		Specification<M> spec = ExpressionUtil.genExpressionByFilter(filter);
		long count = this.getDao().count(spec);
		return count;
	}

	@Override
	public List<M> findByFilter(Map<String, Object> filter) {
		return this.findByFilter(filter, null, null, null, null);
	}
	
	
	@Override
	public List<M> findByFilter(Map<String, Object> filter, String orderBy, String order) {
		return this.findByFilter(filter, null, null, orderBy, order);
	}

	@Override
	public List<M> findByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		Specification<M> spec = ExpressionUtil.genExpressionByFilter(filter);
		List<M> modelList = null;
		if (pageIndex != null) {
			Pageable pageable = JpaUtil.buildPageable(pageIndex, pageSize, orderBy, order);
			modelList = this.getDao().findAll(spec, pageable).getContent();
		} else if (orderBy != null) {
			Sort pageSort = JpaUtil.buildSort(orderBy, order);
			modelList = this.getDao().findAll(spec, pageSort);
		} else {
			modelList = this.getDao().findAll(spec);
		}
		return modelList;
	}

	@Override
	public Page<M> findByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		Pageable pageable = JpaUtil.buildPageable(pageIndex, pageSize, orderBy, order);
		Specification<M> spec = ExpressionUtil.genExpressionByFilter(filter);
		Page<M> pageData = this.getDao().findAll(spec, pageable);
		return pageData;
	}

}
