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

<%
    if (role.equals("1") || role.equals("2")) {%>
<h1>Пурсональный кабінет: <%= login%></h1>

<a href="http://localhost:8080/cabinet/user_settings">Налаштування</a><br/>
<a href="http://localhost:8080/cabinet/subscription_list">Активні підписки</a><br/>
<a href="http://localhost:8080/cabinet/wallet">Гаманець</a><br/>

<%}
    if (role.equals("1")) {%>
<a href="http://localhost:8080/cabinet/admin_console">Консоль адміністратора</a><br/>
<%}%>

</body>
</html>
