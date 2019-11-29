package com.hmq.universe.model.po;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hmq.universe.model.CommonModel;
import com.hmq.universe.model.IPO;


@Entity
@Table(name = "t_bill")
public class Bill extends CommonModel<String> implements IPO {

	private BigDecimal money;

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
}