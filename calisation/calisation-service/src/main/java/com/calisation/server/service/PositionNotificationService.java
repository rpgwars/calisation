package com.calisation.server.service;

import java.util.List;

import com.calisation.server.domain.transfer.presentation.PositionNotificationPresentation;
import com.calisation.server.domain.transfer.request.PositionNotificationRequest;
import com.calisation.server.repository.exception.UserCodeNotFoundException;

public interface PositionNotificationService {
	
	void savePositoinNotification(PositionNotificationRequest positionNotificationRequest) throws UserCodeNotFoundException;
	List<PositionNotificationPresentation> getLatestPositionNotifications(String userCode) throws UserCodeNotFoundException;
	PositionNotificationPresentation getCenterPositionNotification(
			List<PositionNotificationPresentation> positionNotifications); 
	int getZoom(PositionNotificationPresentation centerPosition);

}
