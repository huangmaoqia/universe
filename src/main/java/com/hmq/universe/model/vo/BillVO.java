package com.hmq.universe.model.vo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_bill")
public class BillVO extends CommonModel<String> {

	private BigDecimal money;

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

}