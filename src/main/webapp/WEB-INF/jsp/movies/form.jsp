<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="genres" type="java.util.List<cz.vhromada.catalog.entity.Genre>" scope="request"/>
<jsp:useBean id="languages" type="cz.vhromada.catalog.common.Language[]" scope="request"/>
<jsp:useBean id="subtitles" type="cz.vhromada.catalog.common.Language[]" scope="request"/>
<div class="form-group">
    <label class="col-sm-2 control-label" for="czechName">Czech name</label>

    <div class="col-sm-10">
        <form:input type="text" name="czechName" id="czechName" path="czechName" cssClass="form-control"/>
        <span class="error"><form:errors path="czechName"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="originalName">Original name</label>

    <div class="col-sm-10">
        <form:input type="text" name="originalName" id="originalName" path="originalName" cssClass="form-control"/>
        <span class="error"><form:errors path="originalName"/></span>
    </div>
</div>
<div class="form-group">
    <label class="col-sm-2 control-label" for="year">Year</label>

    <div class="col-sm-10">
        <form:input type="number" name="year" id="year" path="year" min="1930" max="2500" step="1" cssClass="form-control"/>
        <span class="error"><form:errors path="year"/></span>
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
    <label class="col-sm-2 control-label">Media</label>
</div>
<c:forEach begin="0" end="${movie.media.size() - 1}" var="i">
    <c:set var="number" scope="request" value="${i + 1}"/>
    <div class="form-group">
        <label class="col-sm-2 control-label">Medium ${number}</label>
        <c:if test="${i > 0}">
            <form:button type="submit" name="remove${i}" class="btn btn-danger col-sm-1" formnovalidate="formnovalidate">Remove</form:button>
        </c:if>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label" for="medium${number}Hours">Medium ${number} hours</label>

        <div class="col-sm-10">
            <form:input type="number" name="medium${number}Hours" id="medium${number}Hours" path="media[${i}].hours" min="0" max="23" step="1"
                        cssClass="form-control"/>
            <span class="error"><form:errors path="media[${i}].hours"/></span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label" for="medium${number}Minutes">Medium ${number} minutes</label>

        <div class="col-sm-10">
            <form:input type="number" name="medium${number}Minutes" id="medium${number}Minutes" path="media[${i}].minutes" min="0" max="59" step="1"
                        cssClass="form-control"/>
            <span class="error"><form:errors path="media[${i}].minutes"/></span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label" for="medium${number}Seconds">Medium ${number} seconds</label>

        <div class="col-sm-10">
            <form:input type="number" name="medium${number}Seconds" id="medium${number}Seconds" path="media[${i}].seconds" min="0" max="59" step="1"
                        cssClass="form-control"/>
            <span class="error"><form:errors path="media[${i}].seconds"/></span>
        </div>
    </div>
</c:forEach>
<div class="form-group">
    <form:button type="submit" name="add" class="btn btn-success col-sm-offset-2 col-sm-1" formnovalidate="formnovalidate">Add</form:button>
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