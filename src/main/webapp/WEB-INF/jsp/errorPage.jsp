<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="errorMessage" type="java.lang.String" scope="request"/>
<c:out value="${errorMessage}"/>