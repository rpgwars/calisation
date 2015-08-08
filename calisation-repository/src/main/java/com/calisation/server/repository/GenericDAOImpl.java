package com.calisation.server.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDAOImpl<T> implements GenericDAO<T>{

	@PersistenceContext(unitName="komar")
	protected EntityManager entityManager; 
	
	@Override
	public void save(T entity) {
		entityManager.persist(entity);
	}
	
	@Override
	public void delete(T entity){
		entityManager.remove(entity);
	}

}
