<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/shows/list">All shows</a></li>
        <li><a href="${pageContext.request.contextPath}/shows/new">New data</a></li>
        <li><a href="${pageContext.request.contextPath}/shows/add">Add show</a></li>
        <li><a href="${pageContext.request.contextPath}/shows/update">Update positions</a></li>
    </ul>
</div>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/shows/${show}/seasons/list">All seasons</a></li>
        <li><a href="${pageContext.request.contextPath}/shows/${show}/seasons/add">Add season</a></li>
    </ul>
</div>