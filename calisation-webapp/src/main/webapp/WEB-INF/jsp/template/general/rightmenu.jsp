<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
		
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
			<div class="login-form">
				<form action="j_spring_security_check" method="post" >
					<fieldset>
						<div class="control-group">
							<input class="login-field" placeholder="Miejsce na TwÃ³j login" id="j_username" name="j_username" size="20" maxlength="50" type="text"/>
							<label class="login-field-icon fui-man-16" for="j_username"></label>
						</div>

						<div class="control-group">
							<input  class="login-field" placeholder="HasÅo" id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
							<label class="login-field-icon fui-lock-16" for="j_password"></label>
						</div>

						<input class="btn btn-primary btn-large btn-block" type="submit" value="Login"/>
					</fieldset>
				</form>
			</div>
		</div>