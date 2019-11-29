package com.hmq.universe.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.alibaba.fastjson.JSON;
import com.hmq.universe.dao.IGenDao;
import com.hmq.universe.model.IDModel;
import com.hmq.universe.service.IGenViewService;
import com.hmq.universe.utis.DataRelation;
import com.hmq.universe.utis.query.BeanUtils;
import com.hmq.universe.utis.query.Condition;
import com.hmq.universe.utis.query.Expression;
import com.hmq.universe.utis.query.ExpressionUtil;
import com.hmq.universe.utis.query.IGetter;
import com.hmq.universe.utis.query.ISetter;

public class GenViewService<VO, PO extends IDModel<ID>, ID extends Serializable, Dao extends IGenDao<PO, ID>>
		extends GenService<PO, ID, Dao> implements IGenViewService<VO, PO, ID> {
	
	private Class<VO> voClass=null;
	private Class<PO> poClass=null;
	
	private List<DataRelation> columnDataRelations=new ArrayList<>();
	
	protected void addColumnDataRelation(DataRelation r){
		columnDataRelations.add(r);
	}
	
	private List<DataRelation> sonDataRelations=new ArrayList<>();
	
	protected void addSonDataRelation(DataRelation r){
		sonDataRelations.add(r);
	}
	

	@Override
	public VO getVOById(ID id) {
		PO po=this.getById(id);
		VO vo=this.toVO(po);
		relatedColumn(vo);
		relatedSon(vo);
		return vo;
	}

	@Override
	public ID saveOneVO(VO vo) {
		PO po=this.toPO(vo);
		return this.saveOne(po).getId();
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
	public int saveAllVO(List<VO> voList) {
		if(voList==null || voList.size()==0){
			return 0;
		}
		List<PO> poList=this.toPO(voList);
		return this.saveAll(poList).size();
	}
	
	protected void relatedColumn(VO vo){
		List<VO> voList=new ArrayList<>();
		voList.add(vo);
		relatedColumn(voList);
	}
	protected void relatedSon(VO vo){
		List<VO> voList=new ArrayList<>();
		voList.add(vo);
		relatedSon(voList);
	}

	protected void relatedColumn(List<VO> voList){
		for(DataRelation columnDataRelation:columnDataRelations){
			columnDataRelation.relate(voList);
		}
		
	}
	protected void relatedSon(List<VO> voList){
		for(DataRelation sonDataRelation:sonDataRelations){
			sonDataRelation.relate(voList);
		}
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
		Specification<VO> spec = ExpressionUtil.genExpressionByFilter(filter);
		toPOSpec(spec);
		List<PO> poList=this.findBySpec((Specification<PO>) spec);
		List<VO> voList=this.toVO(poList);
		relatedColumn(voList);
		return voList;
	}

	@Override
	public Page<VO> findVOByFilterWithPage(Map<String, Object> filter, Integer pageIndex, Integer pageSize,
			String orderBy, String order) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<VO> findVOBySpec(Specification spec){
		
		List<PO> poList=this.findBySpec(spec);
		List<VO> voList=this.toVO(poList);
		relatedColumn(voList);
		return voList;
	}
	
	private void toPOSpec(Specification voSpec){
		if(voSpec instanceof Expression){
			
			List conditionList=((Expression)voSpec).getExpressionList();
			
			
			
			for (DataRelation columnDataRelation:columnDataRelations) {
				
				Map<IGetter, ISetter> backwardRelation=columnDataRelation.getBackwardRelation();
				Set<IGetter> tgSet=backwardRelation.keySet();
				
				Expression exp=new Expression();
				for(IGetter tg:tgSet){
					
					ISetter ss=backwardRelation.get(tg);
					String columnName=BeanUtils.convertToFieldName(ss);
					for(int i=conditionList.size()-1;i>=0;i--){
						Object condition=conditionList.get(i);
						if(condition instanceof Condition){
							String fieldName=((Condition)condition).getFieldName();
							if(columnName.equals(fieldName)){
								conditionList.remove(i);
								exp.addExp(new Condition(BeanUtils.convertToFieldName(tg),((Condition)condition).getOp(),((Condition)condition).getValue()));
								break;
							}
						}
					}
				}
				List vList=columnDataRelation.getTargetService().findBySpec(exp);
				
				Map<IGetter, IGetter> forwardRelation=columnDataRelation.getForwardRelation();
				Set<IGetter> sgSet=forwardRelation.keySet();
				
				for(IGetter sg:sgSet){
					IGetter tg=forwardRelation.get(sg);
					Set valueSet=new HashSet();
					for(Object v:vList){
						valueSet.add(tg.apply(v));
					}
					((Expression) voSpec).addCdIn(sg, valueSet);
				}
				
			}
		
		}
	}
	

}
