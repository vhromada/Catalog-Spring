<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/categories/list">All book categories</a></li>
        <li><a href="${pageContext.request.contextPath}/categories/new">New data</a></li>
        <li><a href="${pageContext.request.contextPath}/categories/add">Add book category</a></li>
        <li><a href="${pageContext.request.contextPath}/categories/update">Update positions</a></li>
    </ul>
</div>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/list">All books</a></li>
        <li><a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/add">Add book</a></li>
    </ul>
</div>