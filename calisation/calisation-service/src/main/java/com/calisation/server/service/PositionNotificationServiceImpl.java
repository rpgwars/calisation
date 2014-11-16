package com.calisation.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calisation.server.domain.PositionNotification;
import com.calisation.server.domain.UserCode;
import com.calisation.server.domain.transfer.presentation.PositionNotificationPresentation;
import com.calisation.server.domain.transfer.request.PositionNotificationRequest;
import com.calisation.server.repository.PositionNotificationDAO;
import com.calisation.server.repository.UserCodeDAO;
import com.calisation.server.repository.exception.UserCodeNotFoundException;


@Service
public class PositionNotificationServiceImpl implements PositionNotificationService{

	@Autowired
	private PositionNotificationDAO positionNotificationDAO;
	
	@Autowired
	private UserCodeDAO userCodeDAO; 
	
	private static PositionNotificationPresentation defaultCenterPositionNotification; 
	
	private static final double defaultCenterLatitude = 49.94345478; 
	private static final double defaultCenterLongtitude = 18.65861445; 
	private static final int defaultZoom = 6; 
	private static final int zoom = 8; 
	
	static{
		defaultCenterPositionNotification = new PositionNotificationPresentation(); 
		defaultCenterPositionNotification.setLatitude(defaultCenterLatitude);
		defaultCenterPositionNotification.setLongtitude(defaultCenterLongtitude);
	}
	
	@Override
	@Transactional
	public void savePositoinNotification(
			PositionNotificationRequest positionNotificationRequest) throws UserCodeNotFoundException {
		
		UserCode userCode = userCodeDAO.getUserCode(positionNotificationRequest.getUserCode(),
					positionNotificationRequest.getChallange());
		positionNotificationDAO.saveNotification(userCode, positionNotificationRequest);

	}


	@Override
	@Transactional(readOnly = true)
	public List<PositionNotificationPresentation> getLatestPositionNotifications(
			String userCode) throws UserCodeNotFoundException {
		
		UserCode userCodeEntity = userCodeDAO.getUserCode(userCode);
		List<PositionNotification> latestPositionNotifications =
				positionNotificationDAO.getLatestPositionNotifications(userCodeEntity);
		
		List<PositionNotificationPresentation> positionNotificationPresentationList = 
				new ArrayList<PositionNotificationPresentation>(latestPositionNotifications.size());
		
		for(PositionNotification positionNotification : latestPositionNotifications)
			positionNotificationPresentationList.add(positionNotification.getPositionNotificationPresentation());
		
		
		return positionNotificationPresentationList;
		
	}
	
	@Override
	public PositionNotificationPresentation getCenterPositionNotification(
			List<PositionNotificationPresentation> positionNotifications){
		if(positionNotifications.size() == 0)
			return defaultCenterPositionNotification; 
		return positionNotifications.get(0);
	}
	
	@Override
	public int getZoom(
			PositionNotificationPresentation centerPosition) {
		if(centerPosition == defaultCenterPositionNotification)
			return defaultZoom;
		return zoom;
	}

}
