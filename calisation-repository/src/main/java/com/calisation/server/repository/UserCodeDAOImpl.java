package com.calisation.server.repository;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.calisation.server.domain.UserCode;
import com.calisation.server.domain.transfer.request.UserCodeRequest;
import com.calisation.server.repository.exception.UserCodeNotFoundException;

@Repository
public class UserCodeDAOImpl extends GenericDAOImpl<UserCode> implements UserCodeDAO{

	
	@Override
	public UserCode getUserCode(String userCode, String challange)
			throws UserCodeNotFoundException {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); 
		CriteriaQuery<UserCode> criteriaQuery = criteriaBuilder.createQuery(UserCode.class);
		Root<UserCode> userCodeRoot = criteriaQuery.from(UserCode.class);
		
		Predicate userCodePredicate = criteriaBuilder.equal(userCodeRoot.get("userCode"), userCode);
		Predicate challangePredicate = criteriaBuilder.equal(userCodeRoot.get("challange"), challange);
		criteriaQuery.where(criteriaBuilder.and(userCodePredicate,challangePredicate));
		
		TypedQuery<UserCode> typedQuery = entityManager.createQuery(criteriaQuery);
		List<UserCode> resultList = typedQuery.getResultList();
		
		if(resultList == null || resultList.size() == 0)
			throw new UserCodeNotFoundException(); 
		
		return resultList.get(0);
	}

	@Override
	public UserCode getUserCode(String userCode)
			throws UserCodeNotFoundException {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); 
		CriteriaQuery<UserCode> criteriaQuery = criteriaBuilder.createQuery(UserCode.class);
		Root<UserCode> userCodeRoot = criteriaQuery.from(UserCode.class);
		
		Predicate userCodePredicate = criteriaBuilder.equal(userCodeRoot.get("userCode"), userCode);
		criteriaQuery.where(userCodePredicate);
		
		TypedQuery<UserCode> typedQuery = entityManager.createQuery(criteriaQuery);
		List<UserCode> resultList = typedQuery.getResultList();
		
		if(resultList == null || resultList.size() == 0)
			throw new UserCodeNotFoundException(); 
		
		return resultList.get(0);
	}
	
	@Override
	public void saveUserCode(UserCodeRequest userCodeRequest) {
		UserCode userCode = new UserCode(); 
		userCode.setUserCode(userCodeRequest.getUserCode());
		userCode.setChallange(userCodeRequest.getChallange());
		save(userCode);
	}

}
