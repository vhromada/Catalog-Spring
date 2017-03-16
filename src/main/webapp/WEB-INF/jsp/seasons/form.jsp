<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="languages" type="cz.vhromada.catalog.common.Language[]" scope="request"/>
<jsp:useBean id="subtitles" type="cz.vhromada.catalog.common.Language[]" scope="request"/>
<span class="error global"><form:errors path=""/></span>

<div class="form-group">
    <label class="col-sm-2 control-label" for="number">Number</label>

    <div class="col-sm-10">
        <form:input type="number" name="number" id="number" path="number" min="1" max="100" step="1" cssClass="form-control"/>
        <span class="error"><form:errors path="number"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="startYear">Starting year</label>

    <div class="col-sm-10">
        <form:input type="number" name="startYear" id="startYear" path="startYear" min="1930" max="2500" step="1" cssClass="form-control"/>
        <span class="error"><form:errors path="startYear"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="endYear">Ending year</label>

    <div class="col-sm-10">
        <form:input type="number" name="endYear" id="endYear" path="endYear" min="1930" max="2500" step="1" cssClass="form-control"/>
        <span class="error"><form:errors path="endYear"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">Language</label>

    <div class="col-sm-10">
        <c:forEach items="${languages}" var="language">
            <label class="radio custom">
                <form:radiobutton name="language" path="language" value="${language}"/>
                <c:out value="${language}"/>
            </label>
        </c:forEach>
        <span class="error"><form:errors path="language"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">Subtitles</label>

    <div class="col-sm-10">
        <c:forEach items="${subtitles}" var="subtitlesItem">
            <label class="checkbox custom">
                <form:checkbox path="subtitles" value="${subtitlesItem}"/>
                <c:out value="${subtitlesItem}"/>
            </label>
        </c:forEach>
        <span class="error"><form:errors path="subtitles"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="note">Note</label>

    <div class="col-sm-10">
        <form:input type="text" name="note" id="note" path="note" cssClass="form-control"/>
        <span class="error"><form:errors path="note"/></span>
    </div>
</div>