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

import com.hmq.universe.dao.IGenDao;
import com.hmq.universe.model.po.CommonModel;
import com.hmq.universe.model.po.IDModel;
import com.hmq.universe.model.vo.ITokenVO;
import com.hmq.universe.service.IGenService;
import com.hmq.universe.utis.TokenUtil;
import com.hmq.universe.utis.UUIDUtil;
import com.hmq.universe.utis.query.ExpressionUtil;
import com.hmq.universe.utis.query.JpaUtil;

public class GenService<PO extends IDModel<ID>, ID extends Serializable, Dao extends IGenDao<PO, ID>>
		implements IGenService<PO, ID> {

	@Autowired
	private Dao dao;

	protected Dao getDao() {
		return this.dao;
	}

	@Override
	public PO getById(ID id) {
		PO model = this.dao.getOne(id);
		return model;
	}

	@Override
	public void deleteById(ID id) {
		this.dao.deleteById(id);
	}

	@Override
	public PO saveOne(PO po) {
		handleData(po);
		return this.dao.save(po);
	}

	@Override
	public List<PO> saveAll(List<PO> poList) {
		handleData(poList);
		return this.dao.saveAll(poList);
	}

	private void handleData(PO po) {
		ITokenVO<ID> tokenVO = (ITokenVO<ID>) TokenUtil.getTokenVO();
		if(po instanceof CommonModel){
			CommonModel<ID> cpo=(CommonModel<ID>) po; 
			if (cpo.getCreateTime() == null) {
				cpo.setCreateTime(new Date());
			}
			if (cpo.getCreater() == null) {
				cpo.setCreater(tokenVO.getUserid());
			}
			cpo.setUpdateTime(new Date());
			cpo.setModifier(tokenVO.getUserid());
		}

		if (po.getId() == null) {
			po.setId((ID) UUIDUtil.newUUID());
		}
	}

	private void handleData(List<PO> poList) {
		for (PO po : poList) {
			handleData(po);
		}
	}

	@Override
	public long countBySpec(Specification<PO> spec) {
		return this.getDao().count(spec);
	}
	
	@Override
	public List<PO> findBySpec(Specification<PO> spec) {
		return this.findBySpec(spec, null, null, null, null);
	}

	@Override
	public List<PO> findBySpec(Specification<PO> spec, String orderBy, String order) {
		return this.findBySpec(spec, null, null, orderBy, order);
	}
	
	@Override
	public List<PO> findBySpec(Specification<PO> spec, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		List<PO> modelList = null;
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
	public Page<PO> findBySpecWithPage(Specification<PO> spec, Integer pageIndex, Integer pageSize,
			String orderBy, String order) {
		Pageable pageable = JpaUtil.buildPageable(pageIndex, pageSize, orderBy, order);
		Page<PO> pageData = this.getDao().findAll(spec, pageable);
		return pageData;
	}

	@Override
	public long countByFilter(Map<String, Object> filter) {
		Specification<PO> spec = ExpressionUtil.genExpressionByFilter(filter);
		long count = this.getDao().count(spec);
		return count;
	}

	@Override
	public List<PO> findByFilter(Map<String, Object> filter) {
		return this.findByFilter(filter, null, null, null, null);
	}
	
	
	@Override
	public List<PO> findByFilter(Map<String, Object> filter, String orderBy, String order) {
		return this.findByFilter(filter, null, null, orderBy, order);
	}

	@Override
	public List<PO> findByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		Specification<PO> spec = ExpressionUtil.genExpressionByFilter(filter);
		List<PO> modelList = null;
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
	public Page<PO> findByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		Pageable pageable = JpaUtil.buildPageable(pageIndex, pageSize, orderBy, order);
		Specification<PO> spec = ExpressionUtil.genExpressionByFilter(filter);
		Page<PO> pageData = this.getDao().findAll(spec, pageable);
		return pageData;
	}

}
