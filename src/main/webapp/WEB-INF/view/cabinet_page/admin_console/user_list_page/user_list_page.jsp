<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session" />
<%-- Deprecated --%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization/locale" />
<%-- A custom property loader was used to work with Cyrillic (UTF-8 format) --%>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Users list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edition_page/edition_list_page_style.css"
          type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/><br/>

<c:set var="users" value="${requestScope.userList}"/>

<table border="1">
    <caption>${requestScope.localization.getString("admin_console.link.users")}</caption>
    <tr>
        <th id="login">${requestScope.localization.getString("users.text.login")}</th>
        <th id="name">${requestScope.localization.getString("users.text.name")}</th>
        <th id="email">${requestScope.localization.getString("users.text.email")}</th>
        <th id="balance">${requestScope.localization.getString("users.text.balance")}</th>
        <th id="role">${requestScope.localization.getString("users.text.role")}</th>
        <th id="delete_user">${requestScope.localization.getString("users.text.delete")}</th>
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
            <button onclick="document.getElementById('${login}').style.display='block'">${requestScope.localization.getString("users.text.delete")}</button>

            <div id="${login}" class="modal">
                <span onclick="document.getElementById('${login}').style.display='none'" class="close" title="Close Modal">×</span>
                <form class="modal-content" action="${pageContext.request.contextPath}/cabinet/admin_console/user_list/delete_user" method="post">
                    <div class="container">
                        <h1>${login}</h1>
<%--                        <img src="${title_image}" alt="${title}" width="150" height="200"--%>
<%--                             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>--%>

                        <p>${requestScope.localization.getString("users.text.delete_text")}</p>

                        <div class="clearfix">
                            <button type="button" onclick="document.getElementById('${login}').style.display='none'" class="cancel">${requestScope.localization.getString("users.button.cancel")}</button>
                            <button type="submit" name="user_login" value="${login}" class="delete-btn">${requestScope.localization.getString("users.text.delete")}</button>
                        </div>
                    </div>
                </form>
            </div>
        </td>

    </tr>

    </c:forEach>

</body>
</html>
