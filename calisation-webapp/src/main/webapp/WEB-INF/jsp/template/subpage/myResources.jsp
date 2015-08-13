<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<form:form method="post" action="uploadResource" commandName="resource" enctype="multipart/form-data">
    <div>
        <div><form:label path="name">Name of your file:</form:label></div>
        <div>
            <span><form:input path="name"></form:input></span>
            <span><form:errors path="name"></form:errors></span>
        </div>
    </div>
    <div>
        <div><form:label path="file">File to upload:</form:label></div>
        <div>
            <span><form:input type="file" path="file"></form:input></span>
            <span><form:errors path="file"></form:errors></span>
        </div>
    </div>
    <input type="submit" value="Upload"> Press here to upload the file!
</form:form>





<!--<form method="POST" action="uploadResource" enctype="multipart/form-data">
    File to upload: <input type="file" name="file"><br />

    Name: pe="file" e="text" name="name"><br /> <br />
    <input type="submit" value="Upload"> Press here to upload the file!
</form>-->


<article class="article">
	<spring:message code="about.about"/>
</article>