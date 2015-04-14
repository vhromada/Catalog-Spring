<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../shows/menu.jsp"/>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/shows/${show}/seasons/list">All seasons</a></li>
        <li><a href="${pageContext.request.contextPath}/shows/${show}/seasons/add">Add season</a></li>
    </ul>
</div>