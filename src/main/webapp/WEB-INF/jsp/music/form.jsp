<%--suppress XmlDuplicatedId --%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label" for="name">Name</label>

    <div class="col-sm-10">
        <form:input type="text" name="name" id="name" path="name" cssClass="form-control"/>
        <span class="error"><form:errors path="name"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="wikiCz">Czech Wikipedia</label>

    <div class="col-sm-10">
        <form:input type="text" name="wikiCz" id="wikiCz" path="wikiCz" cssClass="form-control"/>
        <span class="error"><form:errors path="wikiCz"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="wikiEn">English Wikipedia</label>

    <div class="col-sm-10">
        <form:input type="text" name="wikiEn" id="wikiEn" path="wikiEn" cssClass="form-control"/>
        <span class="error"><form:errors path="wikiEn"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="mediaCount">Count of media</label>

    <div class="col-sm-10">
        <form:input type="number" name="mediaCount" id="mediaCount" path="mediaCount" min="1" max="100" step="1" cssClass="form-control"/>
        <span class="error"><form:errors path="mediaCount"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="note">Note</label>

    <div class="col-sm-10">
        <form:input type="text" name="note" id="note" path="note" cssClass="form-control"/>
        <span class="error"><form:errors path="note"/></span>
    </div>
</div>