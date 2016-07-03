<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="genres" type="java.util.List<cz.vhromada.catalog.facade.to.GenreTO>" scope="request"/>
<c:choose>
    <c:when test="${not empty genres}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Name</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${genres}" var="genre">
                <tr>
                    <td><c:out value="${genre.name}"/></td>
                    <td>
                        <c:if test="${genres.indexOf(genre) > 0}">
                            <a href="${pageContext.request.contextPath}/genres/moveUp/${genre.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${genres.indexOf(genre) < genres.size() - 1}">
                            <a href="${pageContext.request.contextPath}/genres/moveDown/${genre.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/genres/duplicate/${genre.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/genres/edit/${genre.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/genres/remove/${genre.id}">Remove</a></td>
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
        <th>Count of genres</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${genres.size()}"/></td>
    </tr>
    </tbody>
</table>