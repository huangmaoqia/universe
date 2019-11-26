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

public class GeneralService<Model extends CommonModel<ID>, ID extends Serializable, Dao extends IGeneralDao<Model, ID>>
		implements IGeneralService<Model, ID> {

	@Autowired
	private Dao dao;

	protected Dao getDao() {
		return this.dao;
	}

	public Model getById(ID id) {
		Model model = this.dao.getOne(id);
		return model;
	}

	public void deleteById(ID id) {
		this.dao.deleteById(id);
	}

	public Model saveOne(Model entity) {
		handleData(entity);
		return this.dao.save(entity);
	}

	public List<Model> saveAll(List<Model> entities) {
		handleData(entities);
		return this.dao.saveAll(entities);
	}

	private void handleData(Model entity) {
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

	private void handleData(List<Model> entities) {
		for (Model entity : entities) {
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
		return this.findByFilter(filter, null, null, null, null);
	}

	@Override
	public long countByFilter(Map<String, Object> filter) {
		Specification<Model> spec = ExpressionUtil.genExpressionByFilter(filter);
		long count = this.getDao().count(spec);
		return count;
	}

	@Override
	public List<Model> findByFilter(Map<String, Object> filter, String orderBy, String order) {
		return this.findByFilter(filter, null, null, orderBy, order);
	}

	@Override
	public List<Model> findByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		Specification<Model> spec = ExpressionUtil.genExpressionByFilter(filter);
		List<Model> modelList = null;
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
	public Page<Model> findByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize,
			String orderBy, String order) {
		Pageable pageable = JpaUtil.buildPageable(pageIndex, pageSize, orderBy, order);
		Specification<Model> specs = ExpressionUtil.genExpressionByFilter(filter);
		Page<Model> pageData = this.getDao().findAll(specs, pageable);
		return pageData;
	}
}
