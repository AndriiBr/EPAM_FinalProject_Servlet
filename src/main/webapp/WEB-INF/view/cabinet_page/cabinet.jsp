<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Cabinet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_background/background.css"
          type="text/css">
</head>
<body>

<%
    String username = (String) session.getAttribute("login");
    request.setAttribute("username", username);
    String role = (String) session.getAttribute("role");
    request.setAttribute("role", role);
%>


<% if (role.equals("1")) {%>
<h1>Кабінет адміністратора: ${username}</h1>
<a href="http://localhost:8080/cabinet/user_settings">Налаштування</a><br/>
<a href="http://localhost:8080/cabinet/edition_list">Список видань</a><br/>
<a href="http://localhost:8080/cabinet/user_list">Список користувачів</a><br/>
<%} else if (role.equals("2")) {%>
<h1>Кабінет користувача: ${username}</h1>

<a href="http://localhost:8080/cabinet/user_settings">Налаштування</a><br/>
<a href="http://localhost:8080/cabinet/subscriptions">Активні підписки</a><br/>
<a href="http://localhost:8080/cabinet/account">Рахунок</a><br/>
<%}%>

</body>
</html>
