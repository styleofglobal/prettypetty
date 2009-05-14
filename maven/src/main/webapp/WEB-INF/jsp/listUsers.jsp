<%@ include file="includes.jsp" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>" />

<h1><fmt:message key="user.list.title"/></h1>

<table class="search">
    <tr>
        <th><fmt:message key="user.form.username"/></th>
        <th><fmt:message key="user.form.role"/></th>
    </tr>
<c:forEach var="users" items="${users}">
    <tr>
        <c:url var="deleteUrl" value="/delete.html">
            <c:param name="id" value="${users.id}" />
        </c:url>

    	<td>${users.username}</td>
        <td>${users.role}</td> 
    	<td>
    		<a href='<c:out value="${deleteUrl}"/>'><fmt:message key="button.delete"/></a>
        </td>
    </tr>
</c:forEach>
</table><br/>
<a href='<c:out value="${homeUrl}"/>'>Home</a>