<%@ include file="includes.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>" />

<h1><fmt:message key="user.form.title"/></h1>

<c:url var="url" value="/addUser.html" /> 
<form:form action="${url}" commandName="addUser">
    <form:hidden path="id" />

    <fieldset>
        <div class="form-row">
            <label for="Username"><fmt:message key="user.form.username"/>:</label>
            <span class="input"><form:input path="username" /></span>
        </div>       
        <div class="form-row">
            <label for="Role"><fmt:message key="user.form.role"/>:</label>
            <span class="input"><form:input path="role" /></span>
        </div>
        <div class="form-buttons">
            <div class="button"><input type="submit" value="<fmt:message key="button.save"/>" /></div>
        </div>        
    </fieldset>
</form:form>