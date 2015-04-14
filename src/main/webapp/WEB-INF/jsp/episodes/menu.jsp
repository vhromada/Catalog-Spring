<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../seasons/menu.jsp"/>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/shows/${show}/seasons/${season}/episodes/list">All episodes</a></li>
        <li><a href="${pageContext.request.contextPath}/shows/${show}/seasons/${season}/episodes/add">Add episode</a></li>
    </ul>
</div>