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
    <h3>Connect to Cafe24</h3>

    <form th:th:action="@{/connect2/cafe24}" method="POST">
        <input type="hidden" name="${_csrf.parameterName}" th:value="${_csrf.token}" />
        <input type="hidden" name="scope" value="user_posts,user_photos" />
        <div class="formInfo">
            <p>You aren't connected to Facebook yet. Click the button to connect Spring Social Showcase with your Facebook account.</p>
        </div>
        <p><button type="submit"><img th:src="@{/social/facebook/connect_light_medium_short.gif}"/></button></p>
        <label for="postToWall"><input id="postToWall" type="checkbox" name="postToWall" /> Tell your friends about Spring Social Showcase on your Facebook wall</label>
    </form>
</div>
</body>
</html>
