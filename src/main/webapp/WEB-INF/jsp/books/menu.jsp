<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../categories/menu.jsp"/>
<div class="container-fluid innerMenu">
    <ul class="nav navbar-nav">
        <li><a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/list">All books</a></li>
        <li><a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/add">Add book</a></li>
    </ul>
</div>