package com.hmq.universe.model.po;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

public class Bill extends CommonModel<String> {

	private BigDecimal money;

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	private String createrName;
}