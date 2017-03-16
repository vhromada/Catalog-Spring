<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cat" uri="/WEB-INF/catalog.tld" %>
<jsp:useBean id="songs" type="java.util.List<cz.vhromada.catalog.entity.Song>" scope="request"/>
<jsp:useBean id="music" type="java.lang.Integer" scope="request"/>
<c:choose>
    <c:when test="${not empty songs}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Name</th>
                <th>Length</th>
                <th>Note</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${songs}" var="song">
                <tr>
                    <td><c:out value="${song.name}"/></td>
                    <td><cat:time time="${song.length}"/></td>
                    <td><c:out value="${song.note}"/></td>
                    <td>
                        <c:if test="${songs.indexOf(song) > 0}">
                            <a href="${pageContext.request.contextPath}/music/${music}/songs/moveUp/${song.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${songs.indexOf(song) < songs.size() - 1}">
                            <a href="${pageContext.request.contextPath}/music/${music}/songs/moveDown/${song.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/music/${music}/songs/duplicate/${song.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/music/${music}/songs/edit/${song.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/music/${music}/songs/remove/${song.id}">Remove</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        No records.
    </c:otherwise>
</c:choose>