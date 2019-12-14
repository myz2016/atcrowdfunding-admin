<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${!empty list }">
    <c:forEach items="${list }" var="admin">
        ${admin }<br/>
    </c:forEach>
</c:if>
<input type="text">
</body>
</html>
