<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <footer>

    </footer>
	<%
		String contextPath = request.getContextPath();
	%>

    <!-- <script src="<%=contextPath%><spring:theme code="modernizr"/>"></script> -->
    <script src="<%=contextPath%><spring:theme code="jquery"/>"></script>
    <script src="<%=contextPath%><spring:theme code="jquery-ui"/>"></script>
    <script src="<%=contextPath%><spring:theme code="jquery-plugins"/>"></script>
    <script src="<%=contextPath%><spring:theme code="zepto"/>"></script>
    <script src="<%=contextPath%><spring:theme code="scripts"/>"></script>
