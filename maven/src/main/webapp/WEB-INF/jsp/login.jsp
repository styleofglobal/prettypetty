<%@ include file="includes.jsp"%>

<h1>Welcome <u><%=request.getAttribute("role")%></u></h1>
<c:url var="homeUrl" value="/index.html">
</c:url>
<a href='<c:out value="${homeUrl}"/>'>Home</a>