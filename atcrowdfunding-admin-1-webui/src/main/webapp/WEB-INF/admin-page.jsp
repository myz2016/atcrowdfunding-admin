<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@ include file="/WEB-INF/include-head.jsp" %>
<body>
<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <c:forEach items="${requestScope['PAGE-INFO'].list}" var="admin">
                ${admin}<br>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>