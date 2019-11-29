package com.hmq.universe.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.hmq.universe.model.IDModel;

@NoRepositoryBean
public interface IGenDao<M extends IDModel<ID>, ID extends Serializable>
		extends JpaRepository<M, ID>, JpaSpecificationExecutor<M> {
}
