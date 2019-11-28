package com.hmq.universe.dao;

import org.springframework.stereotype.Repository;

import com.hmq.universe.model.po.User;

@Repository
public interface IUserDao extends IGenDao<User, String> {

}