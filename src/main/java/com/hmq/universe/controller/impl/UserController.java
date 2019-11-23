package com.hmq.universe.controller.impl;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmq.universe.model.po.User;
import com.hmq.universe.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController extends GeneralController<User, String, IUserService>{

	
	@GetMapping("/getById/{id}")
    public User getByIId(@PathVariable String id){
		User user= this.getService().getById(id);
		return user;
	}
	
	
	
	
	@GetMapping("/saveSome")
    public void saveSome(){
		this.getService().add1();
//		this.getService().add2();
		return;
	}
	
}
