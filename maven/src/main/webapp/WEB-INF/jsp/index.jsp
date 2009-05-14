<%@ include file="includes.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>" />

<h1><u>Welcome to Pretty Petty Office Suite</u></h1>

<c:url var="url" value="/login.html" />
<form action="${url}">
    <fieldset>
        <div class="form-row">
            <label for="Username"><fmt:message key="user.form.username"/>:</label>
            <span class="input"><input type="text" name="username" size="10" maxlength="20"></span>
        </div>
        <div class="form-buttons">
            <div class="button"><input type="submit" value="<fmt:message key="button.login"/>" /></div>
        </div>        
    </fieldset>
</form>
<br/>
<a href="<c:url value="/addUser.html"/>">Adduser</a><br/>
<a href="<c:url value="/listUsers.html"/>">ListUsers</a>