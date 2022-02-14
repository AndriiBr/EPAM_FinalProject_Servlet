<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<%-- Deprecated --%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization/locale"/>
<%-- A custom property loader was used to work with Cyrillic (UTF-8 format) --%>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/input_form_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_page/login_page_style.css"
          type="text/css">

</head>
<body>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/><br/>

<%
    String login = (String) session.getAttribute("formLogin");
    String email = (String) session.getAttribute("formEmail");
    String errorFlag = (String) session.getAttribute("errorFlag");
%>

<div class="wrapper fadeInDown">
    <div id="formContent">
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon" alt="User Icon"/>
        </div>

        <% switch (errorFlag) {
            case "1": %>
        <div class="message_error">
            ${requestScope.localization.getString("login.text.password_not_match")}<br>
                ${requestScope.localization.getString("login.text.please_try_again")}
        </div>
        <%
                break;
            case "2":
        %>
        <div class="message_error">
            ${requestScope.localization.getString("login.label.username")}<br/>
        </div>
        <div class="user_message">
            <%= login%><br/>
        </div>
        <div class="message_error">
            ${requestScope.localization.getString("login.text.already_exist")}
        </div>
        <% break;
            case "3": %>
        <div class="message_error">
            ${requestScope.localization.getString("login.label.email")}<br/>
        </div>
        <div class="user_message">
            <%= email%><br/>
        </div>
        <div class="message_error">
            ${requestScope.localization.getString("login.text.already_exist")}
        </div>
        <%}%>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/new_account">${requestScope.localization.getString("login.link.registration_form")}</a>
        </div>
    </div>
</div>
</body>
</html>