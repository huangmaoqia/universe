package com.hmq.universe.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.hmq.universe.model.po.IDModel;

@NoRepositoryBean
public interface IGeneralDao<Model extends IDModel<ID>, ID extends Serializable>
		extends JpaRepository<Model, ID>, JpaSpecificationExecutor<Model> {
}