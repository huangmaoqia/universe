package com.hmq.universe.model.vo;

import com.hmq.universe.model.po.CommonModel;

public class UserVO extends CommonModel<String> {

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}