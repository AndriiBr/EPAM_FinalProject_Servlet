<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Cabinet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
</head>
<body>

<%
    String login = (String) session.getAttribute("login");
    String role = (String) session.getAttribute("role");
%>


<% if (role.equals("1")) {%>
<h1>Кабінет адміністратора: <%= login%></h1>
<a href="http://localhost:8080/cabinet/user_settings">Налаштування</a><br/>
<a href="http://localhost:8080/edition_list">Список видань</a><br/>
<a href="http://localhost:8080/cabinet/user_list">Список користувачів</a><br/>
<a href="http://localhost:8080/cabinet/wallet">Гаманець</a><br/>
<%} else if (role.equals("2")) {%>
<h1>Кабінет користувача: <%= login%></h1>

<a href="http://localhost:8080/cabinet/user_settings">Налаштування</a><br/>
<a href="http://localhost:8080/cabinet/subscriptions">Активні підписки</a><br/>
<a href="http://localhost:8080/cabinet/wallet">Гаманець</a><br/>
<%}%>

</body>
</html>
