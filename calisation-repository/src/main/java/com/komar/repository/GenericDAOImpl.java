package com.komar.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

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
		
		public CriteriaQuery<T> getSimpleCriteria(EntityManager entityManager, Class<T> cls, String column, Object value){
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); 
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(cls);
			Root<T> root = criteriaQuery.from(cls);
			Predicate loginPredicate = criteriaBuilder.equal(root.get(column), value);
			criteriaQuery.where(loginPredicate);
			return criteriaQuery;
		}

		public <U> CriteriaQuery<T> getJoinedCriteriaConditionOnJoined(EntityManager entityManager, Class<T> rootCls, String joinAttribute, String column, Object value) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(rootCls);
			Join<T, U> join = criteriaQuery.from(rootCls).join(joinAttribute);
			Predicate predicate = criteriaBuilder.equal(join.get(column), value);
			criteriaQuery.where(predicate);
			return criteriaQuery;
		}

		public <U> CriteriaQuery<T> getJoinedCriteriaConditionOnJoiningAndJoined(EntityManager entityManager, Class<T> rootCls,
																				   String joinAttribute, String joinedColumn,
																				   Object joinedValue, String joiningColumn, Object joiningValue){
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(rootCls);
			Join<T, U> join = criteriaQuery.from(rootCls).join(joinAttribute);
			Predicate joinedPredicate = criteriaBuilder.equal(join.get(joinedColumn), joinedValue);
			Predicate joiningPredicate = criteriaBuilder.equal(join.get(joiningColumn), joiningValue);
			criteriaQuery.where(criteriaBuilder.and(joinedPredicate, joiningPredicate));
			return criteriaQuery;		}
	}
}
