<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/bookCategories/list">All book categories</a></li>
        <li><a href="${pageContext.request.contextPath}/bookCategories/new">New data</a></li>
        <li><a href="${pageContext.request.contextPath}/bookCategories/add">Add book category</a></li>
        <li><a href="${pageContext.request.contextPath}/bookCategories/update">Update positions</a></li>
    </ul>
</div>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/bookCategories/${bookCategory}/books/list">All books</a></li>
        <li><a href="${pageContext.request.contextPath}/bookCategories/${bookCategory}/books/add">Add book</a></li>
    </ul>
</div>