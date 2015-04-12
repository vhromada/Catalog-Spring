<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="music" type="java.util.List<cz.vhromada.catalog.web.domain.Music>" scope="request"/>
<jsp:useBean id="mediaCount" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="songsCount" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="totalLength" type="cz.vhromada.catalog.commons.Time" scope="request"/>
<c:choose>
    <c:when test="${not empty music}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Name</th>
                <th>Count of media</th>
                <th>Count of songs</th>
                <th>Total length</th>
                <th>Note</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${music}" var="musicItem">
                <tr>
                    <td><c:out value="${musicItem.name}"/></td>
                    <td><c:out value="${musicItem.mediaCount}"/></td>
                    <td><c:out value="${musicItem.songsCount}"/></td>
                    <td><c:out value="${musicItem.totalLength}"/></td>
                    <td><c:out value="${musicItem.note}"/></td>
                    <td>
                        <c:if test="${!empty(musicItem.wikiCz)}">
                            <a href="http://cz.wikipedia.org/wiki/${musicItem.wikiCz}">Czech Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!empty(musicItem.wikiEn)}">
                            <a href="http://en.wikipedia.org/wiki/${musicItem.wikiEn}">English Wikipedia</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/music/${musicItem.id}/songs/list">Songs</a></td>
                    <td>
                        <c:if test="${music.indexOf(musicItem) > 0}">
                            <a href="${pageContext.request.contextPath}/music/moveUp/${musicItem.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${music.indexOf(musicItem) < music.size() - 1}">
                            <a href="${pageContext.request.contextPath}/music/moveDown/${musicItem.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/music/duplicate/${musicItem.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/music/edit/${musicItem.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/music/remove/${musicItem.id}">Remove</a></td>
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
        <th>Count of music</th>
        <th>Count of media</th>
        <th>Count of songs</th>
        <th>Total length</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${music.size()}"/></td>
        <td><c:out value="${mediaCount}"/></td>
        <td><c:out value="${songsCount}"/></td>
        <td><c:out value="${totalLength}"/></td>
    </tr>
    </tbody>
</table>