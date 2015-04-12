<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="bookCategory" type="cz.vhromada.catalog.web.fo.BookCategoryFO" scope="request"/>
<form:form commandName="bookCategory" method="POST" action="${pageContext.request.contextPath}/bookCategories/edit" cssClass="form-horizontal">
    <fieldset>
        <form:input type="hidden" name="id" path="id" value="${bookCategory.id}"/>
        <form:input type="hidden" name="position" path="position" value="${bookCategory.position}"/>
        <jsp:include page="form.jsp"/>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <form:button type="submit" name="create" class="btn btn-primary">Update</form:button>
                <form:button type="submit" name="cancel" class="btn btn-danger">Cancel</form:button>
            </div>
        </div>
    </fieldset>
</form:form>