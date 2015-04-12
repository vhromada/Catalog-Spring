<%--suppress XmlDuplicatedId --%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="languages" type="cz.vhromada.catalog.commons.Language[]" scope="request"/>
<div class="form-group">
    <label class="col-sm-2 control-label" for="author">Author</label>

    <div class="col-sm-10">
        <form:input type="text" name="author" id="author" path="author" cssClass="form-control"/>
        <span class="error"><form:errors path="author"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="title">Title</label>

    <div class="col-sm-10">
        <form:input type="text" name="title" id="title" path="title" cssClass="form-control"/>
        <span class="error"><form:errors path="title"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">Languages</label>

    <div class="col-sm-10">
        <c:forEach items="${languages}" var="language">
            <label class="checkbox custom">
                <form:checkbox path="languages" value="${language}"/>
                <c:out value="${language}"/>
            </label>
        </c:forEach>
        <span class="error"><form:errors path="languages"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="note">Note</label>

    <div class="col-sm-10">
        <form:input type="text" name="note" id="note" path="note" cssClass="form-control"/>
        <span class="error"><form:errors path="note"/></span>
    </div>
</div>