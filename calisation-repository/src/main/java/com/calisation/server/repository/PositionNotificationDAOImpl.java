package com.calisation.server.repository;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.calisation.server.domain.PositionNotification;
import com.calisation.server.domain.UserCode;
import com.calisation.server.domain.transfer.request.PositionNotificationRequest;

@Repository
public class PositionNotificationDAOImpl extends GenericDAOImpl<PositionNotification> implements PositionNotificationDAO{

	private static final int MAX_RESULTS = 5; 
	
	@Override
	public void saveNotification(UserCode userCode,
			PositionNotificationRequest positionNotificationRequest) {
		
		PositionNotification positionNotification = new PositionNotification(); 
		positionNotification.setDate(positionNotificationRequest.getDate());
		positionNotification.setLatitude(positionNotificationRequest.getLatitude());
		positionNotification.setLongtitude(positionNotificationRequest.getLongtitude());
		positionNotification.setTimeZone(positionNotificationRequest.getTimeZone());
		positionNotification.setUserCode(userCode);
		userCode.addPositionNotification(positionNotification);	
	}

	@Override
	public List<PositionNotification> getLatestPositionNotifications(
			UserCode userCode) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); 
		CriteriaQuery<PositionNotification> criteriaQuery = criteriaBuilder.createQuery(PositionNotification.class);
		Root<PositionNotification> positionNotificationRoot = criteriaQuery.from(PositionNotification.class);
		//Join<PositionNotification, UserCode> userCodeJoin = positionNotificationRoot.join("userCode");
		
		Predicate userCodePredicate = criteriaBuilder.equal(positionNotificationRoot.get("userCode"), userCode);
		criteriaQuery.where(userCodePredicate); 
		criteriaQuery.orderBy(criteriaBuilder.desc(positionNotificationRoot.get("date")));
		TypedQuery<PositionNotification> typedQuery = entityManager.createQuery(criteriaQuery);
		typedQuery.setMaxResults(MAX_RESULTS);
		
		List<PositionNotification> resultList = typedQuery.getResultList();
		
		return resultList;

	}

}
