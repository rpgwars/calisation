package com.komar.repository;

import javax.persistence.criteria.CriteriaQuery;

import com.komar.domain.account.exception.NotFound;

public interface GenericDAO<T> {

	void save(T entity);
	void delete(T entity);
	T isExisting(CriteriaQuery<T> criteriaQuery) throws NotFound;
}
