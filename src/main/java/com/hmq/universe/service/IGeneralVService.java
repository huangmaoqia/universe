package com.hmq.universe.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.hmq.universe.model.po.CommonModel;

public interface IGeneralVService<V, M extends CommonModel<ID>, ID extends Serializable>
		extends IGeneralService<M, ID> {

	public V getVOById(ID id);

	public V saveOneVO(V vo);

	public List<V> saveAllVO(List<V> voList);

	public List<V> findVOByFilter(Map<String, Object> filter);

	public List<V> findVOByFilter(Map<String, Object> filter, String orderBy, String order);

	public List<V> findVOByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order);

	public Page<V> findVOByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize,
			String orderBy, String order);
}
