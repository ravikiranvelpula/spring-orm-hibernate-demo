package com.cg.training.springormhibernatedemo.jpa.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.training.springormhibernatedemo.jpa.entity.PersonEntity;

@Repository
@Transactional
public class PersonJPARepository {
	
	@Autowired
	EntityManager entityManager;

	public PersonEntity get(int id){
		return entityManager.find(PersonEntity.class, id);
	}
	
}
