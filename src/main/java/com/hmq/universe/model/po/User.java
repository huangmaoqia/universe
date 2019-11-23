package com.hmq.universe.model.po;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class User extends CommonModel<String> {

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}