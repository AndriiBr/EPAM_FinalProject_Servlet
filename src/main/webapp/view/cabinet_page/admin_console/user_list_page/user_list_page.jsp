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
<jsp:include page="/view/parts/header.jsp"/><br/>

<c:set var="users" value="${requestScope.userList}"/>

<table border="1">
    <caption>${requestScope.localization.getString("admin_console.link.users")}</caption>
    <tr>
        <th id="login">${requestScope.localization.getString("users.text.login")}</th>
        <th id="name">${requestScope.localization.getString("users.text.name")}</th>
        <th id="email">${requestScope.localization.getString("users.text.email")}</th>
        <th id="balance">${requestScope.localization.getString("users.text.balance")}</th>
        <th id="role">${requestScope.localization.getString("users.text.role")}</th>
        <th id="status">${requestScope.localization.getString("users.text.status")}</th>
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
        <c:choose>
            <c:when test="${role == 2}">
                <%-- Block button --%>
                <td>
                    <button class="red-btn" onclick="document.getElementById('${login}').style.display='block'">${requestScope.localization.getString("users.button.block")}</button>

                    <div id="${login}" class="modal">
                        <span onclick="document.getElementById('${login}').style.display='none'" class="close" title="Close Modal">×</span>
                        <form class="modal-content" action="${pageContext.request.contextPath}/cabinet/admin_console/user_list/change_user_status" method="post">
                            <input type="hidden" name="status" value="0" />
                            <input type="hidden" name="userName" value="${login}" />
                            <div class="container">
                                <h1>${login}</h1>

                                <p>${requestScope.localization.getString("users.text.block_text")}</p>

                                <div class="clearfix">
                                    <button type="button" onclick="document.getElementById('${login}').style.display='none'" class="cancel">${requestScope.localization.getString("users.button.cancel")}</button>
                                    <button type="submit" name="user_login" value="${login}" class="delete-btn">${requestScope.localization.getString("users.button.block")}</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </td>
            </c:when>
            <c:when test="${role == 0}">
                <%-- Unblock button --%>
                <td>
                    <button class="green-btn" onclick="document.getElementById('${login}').style.display='block'">${requestScope.localization.getString("users.button.unblock")}</button>

                    <div id="${login}" class="modal">
                        <span onclick="document.getElementById('${login}').style.display='none'" class="close" title="Close Modal">×</span>
                        <form class="modal-content" action="${pageContext.request.contextPath}/cabinet/admin_console/user_list/change_user_status" method="post">
                            <input type="hidden" name="status" value="2" />
                            <input type="hidden" name="userName" value="${login}" />
                            <div class="container">
                                <h1>${login}</h1>

                                <p>${requestScope.localization.getString("users.text.unblock_text")}</p>

                                <div class="clearfix">
                                    <button type="button" onclick="document.getElementById('${login}').style.display='none'" class="cancel">${requestScope.localization.getString("users.button.cancel")}</button>
                                    <button type="submit" name="user_login" value="${login}" class="buy-btn">${requestScope.localization.getString("users.button.unblock")}</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </td>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${role != 1}">
                <%-- Delete button --%>
                <td>
                    <button class="red-btn" onclick="document.getElementById('${login}2').style.display='block'">${requestScope.localization.getString("users.text.delete")}</button>

                    <div id="${login}2" class="modal">
                        <span onclick="document.getElementById('${login}2').style.display='none'" class="close" title="Close Modal">×</span>
                        <form class="modal-content" action="${pageContext.request.contextPath}/cabinet/admin_console/user_list/delete_user" method="post">
                            <input type="hidden" name="userName" value="${login}" />
                            <div class="container">
                                <h1>${login}</h1>

                                <p>${requestScope.localization.getString("users.text.delete_text")}</p>

                                <div class="clearfix">
                                    <button type="button" onclick="document.getElementById('${login}2').style.display='none'" class="cancel">${requestScope.localization.getString("users.button.cancel")}</button>
                                    <button type="submit" name="user_login" value="${login}" class="delete-btn">${requestScope.localization.getString("users.text.delete")}</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </td>
            </c:when>
        </c:choose>
    </tr>
    </c:forEach>
</table>

<%--For displaying Previous link except 1st page --%>
<c:if test="${requestScope.currentPage != 1}">
    <td><a href="http://localhost:8080/cabinet/admin_console/user_list?page=${requestScope.currentPage - 1}">${requestScope.localization.getString("edition_list.button.previous")}</a></td>
</c:if>

<%--For displaying Page numbers--%>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="http://localhost:8080/cabinet/admin_console/user_list?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
<c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
    <td><a href="http://localhost:8080/cabinet/admin_console/user_list?page=${requestScope.currentPage + 1}">${requestScope.localization.getString("edition_list.button.next")}</a></td>
</c:if>

</body>
</html>
