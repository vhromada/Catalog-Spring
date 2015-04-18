<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="episode" type="cz.vhromada.catalog.web.fo.EpisodeFO" scope="request"/>
<jsp:useBean id="show" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="season" type="java.lang.Integer" scope="request"/>
<form:form commandName="episode" method="POST" action="${pageContext.request.contextPath}/shows/${show}/seasons/${season}/episodes/add"
           cssClass="form-horizontal">
    <fieldset>
        <jsp:include page="form.jsp"/>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <form:button type="submit" name="create" class="btn btn-primary">Create</form:button>
                <form:button type="submit" name="cancel" class="btn btn-danger" formnovalidate="formnovalidate">Cancel</form:button>
            </div>
        </div>
    </fieldset>
</form:form>