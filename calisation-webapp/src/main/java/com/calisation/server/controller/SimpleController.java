package com.calisation.server.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calisation.server.domain.transfer.presentation.PositionNotificationPresentation;
import com.calisation.server.service.PositionNotificationService;



@Controller
public class SimpleController {
	
	@Autowired
	private PositionNotificationService positionNotificationService; 
	
	@RequestMapping("/home")
	public String simple(Map<String, Object> map){

		map.put("positionNotificationList", Collections.emptyList());
		PositionNotificationPresentation centerPositionNotification =
				positionNotificationService.getCenterPositionNotification(
						Collections.<PositionNotificationPresentation>emptyList());
		map.put("centerNotification",centerPositionNotification);
		map.put("zoom", positionNotificationService.getZoom(centerPositionNotification));
		
		return "simple";
	}
	
	@RequestMapping("/about")
	public String about(){
		return "about"; 
	}
	

}
