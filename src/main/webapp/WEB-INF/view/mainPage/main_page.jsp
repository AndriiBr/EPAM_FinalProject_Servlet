<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!doctype html>
<head>
    <title>Main page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mainPage/main_page_style.css" type="text/css">
</head>
<body>

<h1>Головна сторінка</h1><br/>

<a href="http://localhost:8080/about">Про нас</a><br/>
<a href="http://localhost:8080/edition">Видання</a><br/>

<% if (session.getAttribute("login") != null) { %>
<a href="http://localhost:8080/cabinet">Особистий кабінет</a><br/>
<a href="http://localhost:8080/logout">Вихід</a><br/>
<% } else { %>
<a href="http://localhost:8080/login">Вхід</a><br/>
<% } %>

</body>