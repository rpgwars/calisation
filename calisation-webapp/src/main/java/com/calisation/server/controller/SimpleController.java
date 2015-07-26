package com.calisation.server.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.calisation.server.domain.transfer.presentation.PositionNotificationPresentation;
import com.calisation.server.service.PositionNotificationService;
import com.komar.domain.transfer.cloudstorage.put.PutResult;
import com.komar.service.cloudstorage.put.PutException;
import com.komar.service.cloudstorage.put.PutService;



@Controller
public class SimpleController {
	
	@Autowired
	private PositionNotificationService positionNotificationService; 
	
	@Autowired
	private PutService putService; 
	
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
	
	@RequestMapping("/sandbox")
	public String sandbox(Map<String, Object> map){
		return "sandbox";
	}
	
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadFileHandler(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
 
        if (!file.isEmpty()) {
        	System.out.println(file.getName());
        	try {
				PutResult put = putService.put(file);
				System.out.println(put.toString());
			} catch (PutException e) {
	        	return new ModelAndView()
        		{
		        	{
		        		setViewName("sandbox2");
		        	}
        		};
			}
        	return new ModelAndView(){
        		{
        			setViewName("uploadSuccess");
        		}
        	};
        } else {
        	return new ModelAndView()
        		{
		        	{
		        		setViewName("sandbox2");
		        	}
        		};
        }
    }

	
	@RequestMapping("/about")
	public String about(){
		return "about"; 
	}

	public void setPutService(PutService putService) {
		this.putService = putService;
	}

}
