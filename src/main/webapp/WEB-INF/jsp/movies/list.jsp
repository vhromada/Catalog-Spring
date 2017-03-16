<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cat" uri="/WEB-INF/catalog.tld" %>
<jsp:useBean id="movies" type="java.util.List<cz.vhromada.catalog.entity.Movie>" scope="request"/>
<jsp:useBean id="mediaCount" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="totalLength" type="cz.vhromada.catalog.common.Time" scope="request"/>
<c:choose>
    <c:when test="${not empty movies}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Czech name</th>
                <th>Original name</th>
                <th>Genres</th>
                <th>Year</th>
                <th>Language</th>
                <th>Subtitles</th>
                <th>Media</th>
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
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${movies}" var="movie">
                <tr>
                    <td><c:out value="${movie.czechName}"/></td>
                    <td><c:out value="${movie.originalName}"/></td>
                    <td><cat:genres genres="${movie.genres}"/></td>
                    <td><c:out value="${movie.year}"/></td>
                    <td><c:out value="${movie.language}"/></td>
                    <td><cat:languages languages="${movie.subtitles}"/></td>
                    <td><cat:media media="${movie.media}"/></td>
                    <td><cat:totalLength media="${movie.media}"/></td>
                    <td><c:out value="${movie.picture}"/></td>
                    <td><c:out value="${movie.note}"/></td>
                    <td>
                        <c:if test="${!empty(movie.csfd)}">
                            <a href="http://www.csfd.cz/film/${movie.csfd}">ČSFD</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${movie.imdbCode > 0}">
                            <a href="http://www.imdb.com/title/tt${movie.imdbCode}">IMDB</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!empty(movie.wikiCz)}">
                            <a href="http://cz.wikipedia.org/wiki/${movie.wikiCz}">Czech Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!empty(movie.wikiEn)}">
                            <a href="http://en.wikipedia.org/wiki/${movie.wikiEn}">English Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${movies.indexOf(movie) > 0}">
                            <a href="${pageContext.request.contextPath}/movies/moveUp/${movie.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${movies.indexOf(movie) < movies.size() - 1}">
                            <a href="${pageContext.request.contextPath}/movies/moveDown/${movie.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/movies/duplicate/${movie.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/movies/edit/${movie.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/movies/remove/${movie.id}">Remove</a></td>
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
        <th>Count of movies</th>
        <th>Total length</th>
        <th>Count of media</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${movies.size()}"/></td>
        <td><c:out value="${totalLength}"/></td>
        <td><c:out value="${mediaCount}"/></td>
    </tr>
    </tbody>
</table>