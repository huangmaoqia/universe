package com.hmq.universe.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.hmq.universe.dao.IGeneralDao;
import com.hmq.universe.model.po.CommonModel;
import com.hmq.universe.service.IGeneralVService;

public class GeneralVService<V, M extends CommonModel<ID>, ID extends Serializable, Dao extends IGeneralDao<M, ID>>
		extends GeneralService<M, ID, Dao> implements IGeneralVService<V, M, ID> {

	@Override
	public V getVOById(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V saveOneVO(V vo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected V toVO(M po, Class<V> voClass) {
		return JSON.parseObject(JSON.toJSONString(po), voClass);
	}
	protected List<V> toVO(List<M> poList, Class<V> voClass) {
		return JSON.parseArray(JSON.toJSONString(poList), voClass);
	}
	
	protected M toPO(V vo, Class<M> poClass) {
		return JSON.parseObject(JSON.toJSONString(vo), poClass);
	}
	protected List<M> toPO(List<V> voList, Class<M> poClass) {
		return JSON.parseArray(JSON.toJSONString(voList), poClass);
	}

	@Override
	public List<V> saveAllVO(List<V> voList) {
		List<M> poList=this.toPO(voList, poClass)
		return null;
	}

	@Override
	public List<V> findVOByFilter(Map<String, Object> filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<V> findVOByFilter(Map<String, Object> filter, String orderBy, String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<V> findVOByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<V> findVOByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize,
			String orderBy, String order) {
		// TODO Auto-generated method stub
		return null;
	}

}
