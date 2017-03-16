<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cat" uri="/WEB-INF/catalog.tld" %>
<jsp:useBean id="episodes" type="java.util.List<cz.vhromada.catalog.entity.Episode>" scope="request"/>
<jsp:useBean id="show" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="season" type="java.lang.Integer" scope="request"/>
<c:choose>
    <c:when test="${not empty episodes}">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Number</th>
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
            <c:forEach items="${episodes}" var="episode">
                <tr>
                    <td><c:out value="${episode.number}"/></td>
                    <td><c:out value="${episode.name}"/></td>
                    <td><cat:time time="${episode.length}"/></td>
                    <td><c:out value="${episode.note}"/></td>
                    <td>
                        <c:if test="${episodes.indexOf(episode) > 0}">
                            <a href="${pageContext.request.contextPath}/shows/${show}/seasons/${season}/episodes/moveUp/${episode.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${episodes.indexOf(episode) < episodes.size() - 1}">
                            <a href="${pageContext.request.contextPath}/shows/${show}/seasons/${season}/episodes/moveDown/${episode.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/shows/${show}/seasons/${season}/episodes/duplicate/${episode.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/shows/${show}/seasons/${season}/episodes/edit/${episode.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/shows/${show}/seasons/${season}/episodes/remove/${episode.id}">Remove</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        No records.
    </c:otherwise>
</c:choose>