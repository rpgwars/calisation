package com.calisation.server.repository;

import java.util.List;

import com.calisation.server.domain.PositionNotification;
import com.calisation.server.domain.UserCode;
import com.calisation.server.domain.transfer.request.PositionNotificationRequest;

public interface PositionNotificationDAO extends GenericDAO<PositionNotification>{

	void saveNotification(UserCode userCode,PositionNotificationRequest positionNotificationRequest);
	List<PositionNotification> getLatestPositionNotifications(UserCode userCode);
}
