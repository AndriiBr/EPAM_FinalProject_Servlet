<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Users list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edition_page/edition_list_page_style.css"
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
        <th id="balance">Рахунок</th>
        <th id="role">Роль</th>
        <th id="delete_user">Видалити</th>
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

        <td>
            <button onclick="document.getElementById('${login}').style.display='block'">Delete user</button>

            <div id="${login}" class="modal">
                <span onclick="document.getElementById('${login}').style.display='none'" class="close" title="Close Modal">×</span>
                <form class="modal-content" action="${pageContext.request.contextPath}/cabinet/admin_console/user_list/delete_user" method="post">
                    <div class="container">
                        <h1>${login}</h1>
<%--                        <img src="${title_image}" alt="${title}" width="150" height="200"--%>
<%--                             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>--%>

                        <p>Delete this user?</p>

                        <div class="clearfix">
                            <button type="button" onclick="document.getElementById('${login}').style.display='none'" class="cancel">Cancel</button>
                            <button type="submit" name="user_login" value="${login}" class="delete-btn">Delete</button>
                        </div>
                    </div>
                </form>
            </div>
        </td>

    </tr>

    </c:forEach>

</body>
</html>
