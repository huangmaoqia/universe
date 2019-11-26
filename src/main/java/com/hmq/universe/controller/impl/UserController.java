package com.hmq.universe.controller.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmq.universe.model.po.User;
import com.hmq.universe.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController extends GeneralController<User, String, IUserService>{
	
}
