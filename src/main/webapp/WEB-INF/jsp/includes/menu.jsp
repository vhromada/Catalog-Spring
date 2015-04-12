<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Catalog</a>
        </div>

        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/shows/list">Shows</a>
            <li><a href="${pageContext.request.contextPath}/games/list">Games</a>
            <li><a href="${pageContext.request.contextPath}/music/list">Music</a>
            <li><a href="${pageContext.request.contextPath}/programs/list">Programs</a>
            <li><a href="${pageContext.request.contextPath}/categories/list">Books</a>
            <li><a href="${pageContext.request.contextPath}/genres/list">Genres</a>
        </ul>
    </div>
</nav>