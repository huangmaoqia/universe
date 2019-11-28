package com.hmq.universe.dao;

import org.springframework.stereotype.Repository;

import com.hmq.universe.model.po.Bill;

@Repository
public interface IBillDao extends IGenDao<Bill, String> {

}