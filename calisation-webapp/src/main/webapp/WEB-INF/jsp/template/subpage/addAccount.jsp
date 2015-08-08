<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>



<div class="mainContent">
    <form:form method="post" modelAttribute="account">
        <div>
            <div><form:label path="name">name</form:label></div>
            <div>
                <span><form:input path="name"></form:input></span>
                <span><form:errors path="name"></form:errors></span>
            </div>
        </div>
        <div>
            <div>
                <div><form:label path="email">email</form:label></div>
                <div>
                    <span><form:input path="email"></form:input></span>
                    <span><form:errors path="email"></form:errors></span>
                </div>
            </div>
        </div>
        <div>
            <div><form:label path="password">password</form:label></div>
            <div>
                <span><form:password path="password"></form:password></span>
                <span><form:errors path="password"></form:errors></span>
            </div>
        </div>
        <div>
            <div><form:label path="repeatedPassword">repeated password</form:label></div>
            <div>
                <span><form:password path="repeatedPassword"></form:password></span>
                <span><form:errors path="repeatedPassword"></form:errors></span>
            </div>
        </div>
        <input type="submit" value="Register"/>
    </form:form>
</div>
