package com.calisation.server.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calisation.server.domain.PositionNotification;
import com.calisation.server.domain.transfer.presentation.PositionNotificationPresentation;
import com.calisation.server.domain.transfer.request.PositionNotificationRequest;
import com.calisation.server.domain.transfer.request.UserCodeRequest;
import com.calisation.server.repository.exception.UserCodeNotFoundException;
import com.calisation.server.service.PositionNotificationService;
import com.calisation.server.service.UserCodeService;
import com.calisation.server.service.exception.UserCodeExistsException;


@Controller
public class LocalisationController {
	
	@Autowired
	private PositionNotificationService positionNotificationService;
	
	@Autowired
	private UserCodeService userCodeService;
	
	@RequestMapping("/notifylocation")
	public void savePositionNotification(@RequestBody PositionNotificationRequest positionNotificationRequest,
			HttpServletResponse response){
			
			try {
				positionNotificationService.savePositoinNotification(positionNotificationRequest);
			} catch (UserCodeNotFoundException e) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return; 
			}
			response.setStatus(HttpServletResponse.SC_OK);	
	}
	
	@RequestMapping("/requestusercode") 
	public @ResponseBody UserCodeRequest requestUserCode(@RequestBody UserCodeRequest userCodeRequest, HttpServletResponse response){
		
		try {
			userCodeService.requestUserCode(userCodeRequest);
			response.setStatus(HttpServletResponse.SC_OK);
			return userCodeRequest; 
		} catch (UserCodeExistsException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null; 
		}
	}
	
	@RequestMapping("/releaseusercode") 
	public @ResponseBody UserCodeRequest releaseUserCode(@RequestBody UserCodeRequest userCodeRequest, HttpServletResponse response){
		

		boolean releaseSuccesfull = userCodeService.releaseUserCode(userCodeRequest);
		if(releaseSuccesfull){
			response.setStatus(HttpServletResponse.SC_OK);
			return userCodeRequest;
		}
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
		
	}
	
	@RequestMapping("/localisation/{userCode}")
	public String presentLocalisation(@PathVariable String userCode, Map<String,Object> map){
		try {
			List<PositionNotificationPresentation> latestPositionNotifications =
					positionNotificationService.getLatestPositionNotifications(userCode);
			prepareLocalisationData(map, latestPositionNotifications);
			return "simple";
		} catch (UserCodeNotFoundException e) {
			map.put("noSuchCode",true);
			prepareLocalisationData(map, Collections.<PositionNotificationPresentation>emptyList());
			return "simple";
		}
		
	}
	
	@RequestMapping("/localisation")
	public String localisation(Map<String,Object> map){
		prepareLocalisationData(map, Collections.<PositionNotificationPresentation>emptyList());
		return "simple";
	}
	
	private void prepareLocalisationData(Map<String,Object> map, 
			List<PositionNotificationPresentation> latestPositionNotifications){
		map.put("positionNotificationList", latestPositionNotifications);
		PositionNotificationPresentation centerPositionNotification =
				positionNotificationService.getCenterPositionNotification(latestPositionNotifications);
		map.put("centerNotification",centerPositionNotification);
		map.put("zoom", positionNotificationService.getZoom(centerPositionNotification));
	}

}
