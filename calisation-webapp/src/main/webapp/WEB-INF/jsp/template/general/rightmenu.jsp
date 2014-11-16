	
		
		<% String name= request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		String protocol; 
		if(request.isSecure())
			protocol = "https://";
		else
			protocol = "http://";
		
		String home = protocol + name + "hello.html";
		String addDoctor = protocol + name + "administration/addDoctor.html";
		String addGroup = protocol + name + "administration/addGroup.html";
		String addCategory = protocol + name + "doctor/addCategory.html";
		%>
		
		<div class="rightmenu">
		
		
			<ul>

			</ul>
		</div>