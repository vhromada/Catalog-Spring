<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../music/menu.jsp"/>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/music/${music}/songs/list">All songs</a></li>
        <li><a href="${pageContext.request.contextPath}/music/${music}/songs/add">Add song</a></li>
    </ul>
</div>