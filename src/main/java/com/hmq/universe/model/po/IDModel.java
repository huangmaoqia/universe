package com.hmq.universe.model.po;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class IDModel<ID extends Serializable> {
	@Id
	private ID id;

	
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
}
