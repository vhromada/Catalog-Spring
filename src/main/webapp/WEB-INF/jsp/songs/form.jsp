<%--suppress XmlDuplicatedId --%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="form-group">
    <label class="col-sm-2 control-label" for="name">Name</label>

    <div class="col-sm-10">
        <form:input type="text" name="name" id="name" path="name" cssClass="form-control"/>
        <span class="error"><form:errors path="name"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">Length</label>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="hours">Hours</label>

    <div class="col-sm-10">
        <form:input type="number" name="hours" id="hours" path="hours" min="0" max="23" step="1" cssClass="form-control"/>
        <span class="error"><form:errors path="hours"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="minutes">Minutes</label>

    <div class="col-sm-10">
        <form:input type="number" name="minutes" id="minutes" path="minutes" min="0" max="59" step="1" cssClass="form-control"/>
        <span class="error"><form:errors path="minutes"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="seconds">Seconds</label>

    <div class="col-sm-10">
        <form:input type="number" name="seconds" id="seconds" path="seconds" min="0" max="59" step="1" cssClass="form-control"/>
        <span class="error"><form:errors path="seconds"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="note">Note</label>

    <div class="col-sm-10">
        <form:input type="text" name="note" id="note" path="note" cssClass="form-control"/>
        <span class="error"><form:errors path="note"/></span>
    </div>
</div>