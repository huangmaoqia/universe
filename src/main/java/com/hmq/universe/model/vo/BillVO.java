package com.hmq.universe.model.vo;

import java.math.BigDecimal;

import com.hmq.universe.model.po.CommonModel;

public class BillVO extends CommonModel<String> {

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