package com.hmq.universe.model.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class CommonModel<ID extends Serializable> extends IDModel<ID>{
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private ID modifier;
	private ID creater;
	
	public Date getCreateTime() {
		return createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public ID getModifier() {
		return modifier;
	}
	public ID getCreater() {
		return creater;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setModifier(ID modifier) {
		this.modifier = modifier;
	}
	public void setCreater(ID creater) {
		this.creater = creater;
	}
}
