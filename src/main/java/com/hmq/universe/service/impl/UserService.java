package com.hmq.universe.service.impl;


import org.hibernate.criterion.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmq.universe.dao.IUserDao;
import com.hmq.universe.model.po.User;
import com.hmq.universe.service.IUserService;

@Service
@Transactional
public class UserService extends GeneralService<User, String, IUserDao> implements IUserService {

	@Override
	public void add1() {
		User user=new User();
		user.setUserName("u1");
		this.saveOne(user);
		
		User user2=new User();
		user2.setUserName("u2");
		
		this.saveOne(user2);
		if(user2.getUserName().equals("u2")){
			throw new RuntimeException();
		}
	}

	@Override
	public void add2() {
		
	}

	
	
}
