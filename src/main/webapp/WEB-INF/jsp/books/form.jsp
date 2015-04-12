<%--suppress XmlDuplicatedId --%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<span class="error global"><form:errors path=""/></span>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label" for="author">Author</label>

    <div class="col-sm-10">
        <form:input type="text" name="author" id="author" path="author" cssClass="form-control"/>
        <span class="error"><form:errors path="author"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label" for="title">Title</label>

    <div class="col-sm-10">
        <form:input type="text" name="title" id="title" path="title" cssClass="form-control"/>
        <span class="error"><form:errors path="title"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label">Language</label>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="czech">Czech</label>

    <div class="col-sm-10">
        <form:checkbox name="crack" id="czech" path="czech"/>
        <span class="error"><form:errors path="czech"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="english">English</label>

    <div class="col-sm-10">
        <form:checkbox name="crack" id="english" path="english"/>
        <span class="error"><form:errors path="english"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="note">Note</label>

    <div class="col-sm-10">
        <form:input type="text" name="note" id="note" path="note" cssClass="form-control"/>
        <span class="error"><form:errors path="note"/></span>
    </div>
</div>