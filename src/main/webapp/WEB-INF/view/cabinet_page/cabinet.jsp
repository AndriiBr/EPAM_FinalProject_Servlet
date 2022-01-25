<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cabinet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_background/background.css"
          type="text/css">
</head>
<body>

<%
    String username = (String) session.getAttribute("login");
    request.setAttribute("username", username);
%>

<h1>Кабінет користувача: ${username}</h1>

<a href="http://localhost:8080/user_settings">Налаштування</a><br/>
<a href="http://localhost:8080/subscriptions">Активні підписки</a><br/>

</body>
</html>
