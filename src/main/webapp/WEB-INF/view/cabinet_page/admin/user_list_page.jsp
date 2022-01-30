<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Users list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_background/background.css"
          type="text/css">
</head>
<body>

<c:set var="users" value="${requestScope.userList}"/>

<table border="1">
    <caption>Список користувачів</caption>
    <tr>
        <th id="login">Логін</th>
        <th id="name">Ім'я</th>
        <th id="email">Пошта</th>
        <th id="balance">рахунок</th>
        <th id="role">Роль</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <c:set var="login" value="${user.login}"/>
        <c:set var="name" value="${user.name}"/>
        <c:set var="email" value="${user.email}"/>
        <c:set var="balance" value="${user.balance}"/>
        <c:set var="role" value="${user.role}"/>

    <tr>
        <td>${login}</td>
        <td>${name}</td>
        <td>${email}</td>
        <td>${balance}</td>
        <td>${role}</td>
    </tr>

    </c:forEach>

</body>
</html>
