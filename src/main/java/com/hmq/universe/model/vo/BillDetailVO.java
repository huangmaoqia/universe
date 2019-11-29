package com.hmq.universe.model.vo;

import com.hmq.universe.model.CommonModel;
import com.hmq.universe.model.IVO;

public class BillDetailVO extends CommonModel<String> implements IVO{

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