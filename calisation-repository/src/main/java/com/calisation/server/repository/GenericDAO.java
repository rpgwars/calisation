package com.calisation.server.repository;

public interface GenericDAO<T> {

	void save(T entity);
	void delete(T entity);
}
