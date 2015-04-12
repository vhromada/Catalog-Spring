<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bookCategories" type="java.util.List<cz.vhromada.catalog.web.domain.BookCategory>" scope="request"/>
<jsp:useBean id="booksCount" type="java.lang.Integer" scope="request"/>
<c:choose>
    <c:when test="${not empty bookCategories}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Name</th>
                <th>Count of books</th>
                <th>Note</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bookCategories}" var="bookCategory">
                <tr>
                    <td><c:out value="${bookCategory.name}"/></td>
                    <td><c:out value="${bookCategory.booksCount}"/></td>
                    <td><c:out value="${bookCategory.note}"/></td>
                    <td><a href="${pageContext.request.contextPath}/bookCategories/${bookCategory.id}/books/list">Books</a></td>
                    <td>
                        <c:if test="${bookCategories.indexOf(bookCategory) > 0}">
                            <a href="${pageContext.request.contextPath}/bookCategories/moveUp/${bookCategory.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${bookCategories.indexOf(bookCategory) < bookCategories.size() - 1}">
                            <a href="${pageContext.request.contextPath}/bookCategories/moveDown/${bookCategory.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/bookCategories/duplicate/${bookCategory.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/bookCategories/edit/${bookCategory.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/bookCategories/remove/${bookCategory.id}">Remove</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        No records.
    </c:otherwise>
</c:choose>
<table class="table">
    <thead>
    <tr>
        <th>Count of book categories</th>
        <th>Count of books</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${bookCategories.size()}"/></td>
        <td><c:out value="${booksCount}"/></td>
    </tr>
    </tbody>
</table>