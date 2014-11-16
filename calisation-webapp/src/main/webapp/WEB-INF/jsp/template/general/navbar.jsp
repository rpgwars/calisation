<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<% String contextPath = request.getContextPath();			
    String home = contextPath;
    String about = contextPath + "/about.html";
    String localisation = contextPath + "/localisation.html";
%>

<header>
    <a href="<%=contextPath%>" class="mainLogo">
        <img src="<%=contextPath%>/themes/img/logo.png" />
        <h4> Calisation
            <span><spring:message code="nav.subtitle"/></span>
        </h4>
    </a>
    <div class="navbar navbar-inverse">
        <div class="navbar-inner">
            <div class="container">
                <div class="nav-collapse collapse">
                   <ul class="nav">
                        <li>
                            <a href="<%=about%>">
                               <spring:message code="nav.about"/>
                                <span class="navbar-unread">1</span>
                            </a>
                        </li>
		                <li>
                               <a href="<%=localisation%>"><spring:message code="nav.localisation"/></a>
                        </li>
                   </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
    </div>
</header>
