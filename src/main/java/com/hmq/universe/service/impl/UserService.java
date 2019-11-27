package com.hmq.universe.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmq.universe.dao.IUserDao;
import com.hmq.universe.model.po.User;
import com.hmq.universe.model.vo.UserVO;
import com.hmq.universe.service.IUserService;

@Service
@Transactional
public class UserService extends GeneralVService<UserVO, User, String, IUserDao> implements IUserService {

}
