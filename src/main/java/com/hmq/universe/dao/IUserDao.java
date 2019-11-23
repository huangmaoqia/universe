package com.hmq.universe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hmq.universe.model.po.User;

@Repository
public interface IUserDao extends IGeneralDao<User, String> {

	@Query(value = "SELECT p FROM User p")
	List<User> queryUser();

}