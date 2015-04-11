<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cat" uri="/WEB-INF/catalog.tld" %>
<jsp:useBean id="games" type="java.util.List<cz.vhromada.catalog.facade.to.GameTO>" scope="request"/>
<jsp:useBean id="mediaCount" type="java.lang.Integer" scope="request"/>
<c:choose>
    <c:when test="${not empty games}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Name</th>
                <th>Count of media</th>
                <th>Additional data</th>
                <th>Note</th>
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
            <c:forEach items="${games}" var="game">
                <tr>
                    <td><c:out value="${game.name}"/></td>
                    <td><c:out value="${game.mediaCount}"/></td>
                    <td><cat:gameAdditionalData game="${game}"/></td>
                    <td><c:out value="${game.note}"/></td>
                    <td>
                        <c:if test="${!empty(game.wikiCz)}">
                            <a href="http://cz.wikipedia.org/wiki/${game.wikiCz}">Czech Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!empty(game.wikiEn)}">
                            <a href="http://en.wikipedia.org/wiki/${game.wikiEn}">English Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${games.indexOf(game) > 0}">
                            <a href="${pageContext.request.contextPath}/games/moveUp/${game.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${games.indexOf(game) < games.size() - 1}">
                            <a href="${pageContext.request.contextPath}/games/moveDown/${game.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/games/duplicate/${game.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/games/edit/${game.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/games/remove/${game.id}">Remove</a></td>
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
        <th>Count of games</th>
        <th>Count of media</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${games.size()}"/></td>
        <td><c:out value="${mediaCount}"/></td>
    </tr>
    </tbody>
</table>