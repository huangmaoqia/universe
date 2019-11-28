package com.hmq.universe.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSON;
import com.hmq.universe.dao.IGenDao;
import com.hmq.universe.model.po.IDModel;
import com.hmq.universe.service.IGenViewService;

public class GenViewService<VO, PO extends IDModel<ID>, ID extends Serializable, Dao extends IGenDao<PO, ID>>
		extends GenService<PO, ID, Dao> implements IGenViewService<VO, PO, ID> {
	
	private Class<VO> voClass=null;
	private Class<PO> poClass=null;

	@Override
	public VO getVOById(ID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VO saveOneVO(VO vo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void inferType(Class<?> clazz){
		String clazzName=clazz.getName();
		String type=clazzName.substring(clazzName.length()-2);
		if("vo".equalsIgnoreCase(type)){
			voClass=(Class<VO>) clazz;
			clazzName=clazzName.replace(".vo.", ".po.");
			try {
				poClass=(Class<PO>) this.getClass().getClassLoader().loadClass(clazzName.substring(0, clazzName.length()-2));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			poClass=(Class<PO>) clazz;
			clazzName=clazzName.replace(".po.", ".vo.");
			try {
				voClass=(Class<VO>) this.getClass().getClassLoader().loadClass(clazzName+"VO");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void inferVOPOClass(Object obj){
		if(voClass!= null)return;
		if(obj==null) return;
		if(obj instanceof List){
			if(((List)obj).size()==0) return;
			inferType(((List)obj).get(0).getClass());
		}else{
			inferType(obj.getClass());
		}
	}
	
	protected VO toVO(PO po) {
		inferVOPOClass(po);
		VO vo= JSON.parseObject(JSON.toJSONString(po), voClass);
		return vo;
	}
	protected List<VO> toVO(List<PO> poList) {
		inferVOPOClass(poList);
		return JSON.parseArray(JSON.toJSONString(poList), voClass);
	}
	
	protected PO toPO(VO vo) {
		inferVOPOClass(vo);
		return JSON.parseObject(JSON.toJSONString(vo), poClass);
	}
	protected List<PO> toPO(List<VO> voList) {
		inferVOPOClass(voList);
		return JSON.parseArray(JSON.toJSONString(voList), poClass);
	}

	@Override
	public List<VO> saveAllVO(List<VO> voList) {
//		List<M> poList=this.toPO(voList, poClass)
		return null;
	}

	protected void refVO(List<VO> voList){
		
	}
	
	@Override
	public List<VO> findVOByFilter(Map<String, Object> filter) {
		return this.findVOByFilter(filter, null, null, null, null);
	}

	@Override
	public List<VO> findVOByFilter(Map<String, Object> filter, String orderBy, String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VO> findVOByFilter(Map<String, Object> filter, Integer pageIndex, Integer pageSize, String orderBy,
			String order) {
		List<PO> poList=this.findByFilter(filter, pageIndex, pageSize, orderBy, order);
		List<VO> voList=this.toVO(poList);
		refVO(voList);
		return voList;
	}

	@Override
	public Page<VO> findVOByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize,
			String orderBy, String order) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
