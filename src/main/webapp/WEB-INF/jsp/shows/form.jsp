<%--suppress XmlDuplicatedId --%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="genres" type="java.util.List<cz.vhromada.catalog.facade.to.GenreTO>" scope="request"/>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label" for="czechName">Czech name</label>

    <div class="col-sm-10">
        <form:input type="text" name="czechName" id="czechName" path="czechName" cssClass="form-control"/>
        <span class="error"><form:errors path="czechName"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 col-sm-2 control-label" for="originalName">Original name</label>

    <div class="col-sm-10">
        <form:input type="text" name="originalName" id="originalName" path="originalName" cssClass="form-control"/>
        <span class="error"><form:errors path="originalName"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="csfd">ČSFD</label>

    <div class="col-sm-10">
        <form:input type="text" name="csfd" id="csfd" path="csfd" cssClass="form-control"/>
        <span class="error"><form:errors path="csfd"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="imdb">IMDB</label>

    <div class="col-sm-10">
        <form:checkbox name="imdb" id="imdb" path="imdb" onclick="imdbShow();"/>
        <div id="imdbPanel">
            <form:input type="number" name="imdbCode" id="imdbCode" path="imdbCode" min="1" max="9999999" step="1" cssClass="form-control"/>
            <span class="help-inline error"><form:errors path=""/></span>
            <span class="help-inline error"><form:errors path="imdb"/></span>
            <span class="help-inline error"><form:errors path="imdbCode"/></span>
        </div>
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
    <label class="col-sm-2 control-label" for="picture">Picture</label>

    <div class="col-sm-10">
        <form:input type="text" name="picture" id="picture" path="picture" cssClass="form-control"/>
        <span class="error"><form:errors path="picture"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="note">Note</label>

    <div class="col-sm-10">
        <form:input type="text" name="note" id="note" path="note" cssClass="form-control"/>
        <span class="error"><form:errors path="note"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="genres">Genres</label>

    <div class="col-sm-10">
        <form:select multiple="true" name="genres" id="genres" path="genres" items="${genres}" itemValue="id" itemLabel="name" cssClass="form-control"/>
        <span class="error"><form:errors path="genres"/></span>
    </div>
</div>