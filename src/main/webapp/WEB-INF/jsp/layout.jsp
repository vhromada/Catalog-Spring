<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<jsp:useBean id="title" type="java.lang.String" scope="request"/>
<!DOCTYPE html>
<html>
<tiles:insertAttribute name="head"/>
<body>
<tiles:insertAttribute name="menu"/>
<tiles:insertAttribute name="innerMenu"/>
<div class="container-fluid">
    <h2>${title}</h2>
    <tiles:insertAttribute name="body"/>
</div>
</body>
</html>