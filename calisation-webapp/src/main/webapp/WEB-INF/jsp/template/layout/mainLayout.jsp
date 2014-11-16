<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
	<tiles:insertAttribute name="header"/>
	<body>
        <tiles:insertAttribute name="navBar"/>
		<div>
			<tiles:insertAttribute name="body"/>
			<tiles:insertAttribute name="rightmenu"/>
		</div>
		<tiles:insertAttribute name="footer"/>
	</body>
</html>