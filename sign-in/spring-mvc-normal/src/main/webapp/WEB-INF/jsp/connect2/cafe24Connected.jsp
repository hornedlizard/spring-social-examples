<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: bit
  Date: 2018-06-28
  Time: 오후 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<head>
    <title>Spring Social Showcase</title>
</head>
<body>
<div id="header">
    <h1><a th:href="@{/}">Spring Social Showcase</a></h1>
</div>

<div id="content" layout:fragment="content">
    <h3>Connected to Facebook</h3>

    <form id="disconnect" method="post">
        <input type="hidden" name="_csrf" th:th:value="${_csrf.token}" />
        <div class="formInfo">
            <p>
                Spring Social Showcase is connected to your Facebook account.
                Click the button if you wish to disconnect.
            </p>
        </div>
        <button type="submit">Disconnect</button>
        <input type="hidden" name="_method" value="delete" />
    </form>

    <a th:href="@{/cafe24}">View your Cafe24 profile</a>
</div>
</body>
</html>
