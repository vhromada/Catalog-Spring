<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cat" uri="/WEB-INF/catalog.tld" %>
<jsp:useBean id="books" type="java.util.List<cz.vhromada.catalog.facade.to.BookTO>" scope="request"/>
<jsp:useBean id="bookCategory" type="java.lang.Integer" scope="request"/>
<c:choose>
    <c:when test="${not empty books}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Author</th>
                <th>Title</th>
                <th>Languages</th>
                <th>Note</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td><c:out value="${book.author}"/></td>
                    <td><c:out value="${book.title}"/></td>
                    <td><cat:languages languages="${book.languages}"/></td>
                    <td><c:out value="${book.note}"/></td>
                    <td>
                        <c:if test="${books.indexOf(book) > 0}">
                            <a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/moveUp/${book.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${books.indexOf(book) < books.size() - 1}">
                            <a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/moveDown/${book.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/duplicate/${book.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/edit/${book.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/categories/${bookCategory}/books/remove/${book.id}">Remove</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        No records.
    </c:otherwise>
</c:choose>