<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cat" uri="/WEB-INF/catalog.tld" %>
<jsp:useBean id="shows" type="java.util.List<cz.vhromada.catalog.web.domain.Show>" scope="request"/>
<jsp:useBean id="seasonsCount" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="episodesCount" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="totalLength" type="cz.vhromada.catalog.commons.Time" scope="request"/>
<c:choose>
    <c:when test="${not empty shows}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Czech name</th>
                <th>Original name</th>
                <th>Genres</th>
                <th>Count of seasons</th>
                <th>Count of episodes</th>
                <th>Total length</th>
                <th>Picture</th>
                <th>Note</th>
                <th></th>
                <th></th>
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
            <c:forEach items="${shows}" var="show">
                <tr>
                    <td><c:out value="${show.czechName}"/></td>
                    <td><c:out value="${show.originalName}"/></td>
                    <td><cat:genres genres="${show.genres}"/></td>
                    <td><c:out value="${show.seasonsCount}"/></td>
                    <td><c:out value="${show.episodesCount}"/></td>
                    <td><c:out value="${show.totalLength}"/></td>
                    <td><c:out value="${show.picture}"/></td>
                    <td><c:out value="${show.note}"/></td>
                    <td>
                        <c:if test="${!empty(show.csfd)}">
                            <a href="http://www.csfd.cz/film/${show.csfd}">ČSFD</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${show.imdbCode > 0}">
                            <a href="http://www.imdb.com/title/tt${show.imdbCode}">IMDB</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!empty(show.wikiCz)}">
                            <a href="http://cz.wikipedia.org/wiki/${show.wikiCz}">Czech Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!empty(show.wikiEn)}">
                            <a href="http://en.wikipedia.org/wiki/${show.wikiEn}">English Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${shows.indexOf(show) > 0}">
                            <a href="${pageContext.request.contextPath}/shows/moveUp/${show.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${shows.indexOf(show) < shows.size() - 1}">
                            <a href="${pageContext.request.contextPath}/shows/moveDown/${show.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/shows/${show.id}/seasons">Seasons</a></td>
                    <td><a href="${pageContext.request.contextPath}/shows/duplicate/${show.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/shows/edit/${show.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/shows/remove/${show.id}">Remove</a></td>
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
        <th>Count of shows</th>
        <th>Count of seasons</th>
        <th>Count of episodes</th>
        <th>Total length</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${shows.size()}"/></td>
        <td><c:out value="${seasonsCount}"/></td>
        <td><c:out value="${episodesCount}"/></td>
        <td><c:out value="${totalLength}"/></td>
    </tr>
    </tbody>
</table>