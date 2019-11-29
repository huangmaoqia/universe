package com.hmq.universe.model.vo;

import com.hmq.universe.model.CommonModel;
import com.hmq.universe.model.IVO;

public class UserVO extends CommonModel<String> implements IVO {

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}