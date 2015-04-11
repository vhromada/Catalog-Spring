<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/music/list">All music</a></li>
        <li><a href="${pageContext.request.contextPath}/music/new">New data</a></li>
        <li><a href="${pageContext.request.contextPath}/music/add">Add music</a></li>
        <li><a href="${pageContext.request.contextPath}/music/update">Update positions</a></li>
    </ul>
</div>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/music/${music}/songs/list">All songs</a></li>
        <li><a href="${pageContext.request.contextPath}/music/${music}/songs/add">Add song</a></li>
    </ul>
</div>