package com.hmq.universe.model.po;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.hmq.universe.model.CommonModel;
import com.hmq.universe.model.IPO;


@Entity
@Table(name = "t_bill_detail")
public class BillDetail extends CommonModel<String> implements IPO {
	
	private String billId;
	
	private String goodsId;

	private long amount;

	public String getBillId() {
		return billId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public long getAmount() {
		return amount;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	
}