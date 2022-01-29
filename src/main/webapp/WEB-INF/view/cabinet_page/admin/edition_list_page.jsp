<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Edition list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_background/background.css"
          type="text/css">
</head>
<body>

<c:set var="editions" value="${requestScope.editionList}"/>


<h1>Список видань</h1>
<a href="http://localhost:8080/add_new_edition">Додати видання</a><br/>

<table border="1">
    <tr>
        <td>Назва</td>
        <td>Картинка</td>
        <td>Ціна</td>
    </tr>
    <c:forEach items="${editions}" var="edition">
        <c:set var="title" value="${edition.title}"/>
        <c:set var="title_image" value="${edition.imagePath}"/>
        <c:set var="price" value="${edition.price}"/>

        <tr>
            <td>${title}</td>
            <td><img src="${title_image}" width="150" height="200" alt="${title}"></td>
            <td>${price}</td>
        </tr>

    </c:forEach>
</table>




</body>
</html>
