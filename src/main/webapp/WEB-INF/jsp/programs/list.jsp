<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cat" uri="/WEB-INF/catalog.tld" %>
<jsp:useBean id="programs" type="java.util.List<cz.vhromada.catalog.entity.Program>" scope="request"/>
<jsp:useBean id="mediaCount" type="java.lang.Integer" scope="request"/>
<c:choose>
    <c:when test="${not empty programs}">
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
            <c:forEach items="${programs}" var="program">
                <tr>
                    <td><c:out value="${program.name}"/></td>
                    <td><c:out value="${program.mediaCount}"/></td>
                    <td><cat:programAdditionalData program="${program}"/></td>
                    <td><c:out value="${program.note}"/></td>
                    <td>
                        <c:if test="${!empty(program.wikiCz)}">
                            <a href="http://cz.wikipedia.org/wiki/${program.wikiCz}">Czech Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${!empty(program.wikiEn)}">
                            <a href="http://en.wikipedia.org/wiki/${program.wikiEn}">English Wikipedia</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${programs.indexOf(program) > 0}">
                            <a href="${pageContext.request.contextPath}/programs/moveUp/${program.id}">Move up</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${programs.indexOf(program) < programs.size() - 1}">
                            <a href="${pageContext.request.contextPath}/programs/moveDown/${program.id}">Move down</a>
                        </c:if>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/programs/duplicate/${program.id}">Duplicate</a></td>
                    <td><a href="${pageContext.request.contextPath}/programs/edit/${program.id}">Edit</a></td>
                    <td><a href="${pageContext.request.contextPath}/programs/remove/${program.id}">Remove</a></td>
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
        <th>Count of programs</th>
        <th>Count of media</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><c:out value="${programs.size()}"/></td>
        <td><c:out value="${mediaCount}"/></td>
    </tr>
    </tbody>
</table>