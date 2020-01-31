<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/15
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <title>❤汇聚点滴的力量，成就非凡的伟业❤</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <script src="/jquery/jquery-2.1.1.min.js"></script>
    <script type="application/javascript" charset="utf-8">
        $(function () {
            $("#back").click(function () {
                window.history.back();
            });
        });
    </script>
    <h1>${requestScope.exception.message}</h1>
    <button id="back" class="btn btn-lg btn-success btn-block">后退</button>
</div>
</body>
</html>