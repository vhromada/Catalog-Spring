<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cat" uri="/WEB-INF/catalog.tld" %>
<jsp:useBean id="seasons" type="java.util.List<cz.vhromada.catalog.web.domain.SeasonData>" scope="request"/>
<jsp:useBean id="show" type="java.lang.Integer" scope="request"/>
<c:choose>
    <c:when test="${not empty seasons}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Number of season</th>
                <th>Year</th>
                <th>Language</th>
                <th>Subtitles</th>
                <th>Episodes count</th>
                <th>Total length</th>
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
            <c:forEach items="${seasons}" var="season">
                <tr>
                    <td><c:out value="${season.season.number}"/></td>
                    <td><cat:years startYear="${season.season.startYear}" endYear="${season.season.endYear}"/></td>
                    <td><c:out value="${season.season.language}"/></td>
                    <td><cat:languages languages="${season.season.subtitles}"/></td>
                    <td><c:out value="${season.episodesCount}"/></td>
                    <td><c:out value="${season.totalLength}"/></td>
                    <td><c:out value="${season.season.note}"/></td>
                    <td><a href="${pageContext.request.contextPath}/shows/${show}/seasons/${season.season.id}/episodes/list">Episodes</a></td>
                    <td>
                        <c:if test="${seasons.indexOf(season) > 0}">
                            <a href="${pageContext.request.contextPath}/shows/${show}/seasons/moveUp/${season.season.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${seasons.indexOf(season) < seasons.size() - 1}">
                            <a href="${pageContext.request.contextPath}/shows/${show}/seasons/moveDown/${season.season.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/shows/${show}/seasons/duplicate/${season.season.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/shows/${show}/seasons/edit/${season.season.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/shows/${show}/seasons/remove/${season.season.id}">Remove</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        No records.
    </c:otherwise>
</c:choose>