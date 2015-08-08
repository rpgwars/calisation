package com.komar.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.komar.domain.account.exception.NotFound;

public class GenericDAOImpl<T> implements GenericDAO<T>{

	@PersistenceContext(unitName="komar")
	protected EntityManager entityManager; 
	
	protected QueryUtils<T> queryUtils = new QueryUtils<T>();
	
	@Override
	public void save(T entity) {
		entityManager.persist(entity);
	}

	@Override
	public void delete(T entity) {
		entityManager.remove(entity);
	}

	@Override
	public T isExisting(CriteriaQuery<T> criteriaQuery) throws NotFound{
		TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
		List<T> resultList = typedQuery.getResultList();
		
		if(resultList.isEmpty())
			throw new NotFound();
		
		return resultList.get(0);
	}
	
	public static class QueryUtils<T>{
		
		public CriteriaQuery<T> getSimpleCriteria(EntityManager entityManager, Class<T> cls, String column, String value){
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); 
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(cls);
			Root<T> accountRoot = criteriaQuery.from(cls);
			Predicate loginPredicate = criteriaBuilder.equal(accountRoot.get(column), value);
			criteriaQuery.where(loginPredicate);
			return criteriaQuery;
		}
	}
}
