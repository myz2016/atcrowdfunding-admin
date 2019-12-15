<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/15
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>❤汇聚点滴的力量，成就非凡的伟业❤</title>
</head>
<body>
<h1>后台主页面</h1>
${sessionScope['LOGIN-ADMIN']}
<%--由于减号（-）在el表达式中有特殊含义，所以如果使用下面的方式就变成了 sessionScope.LOGIN 的值减去 sessionScope.ADMIN 的值-->
<%--${sessionScope.LOGIN-ADMIN}--%>
</body>
</html>
