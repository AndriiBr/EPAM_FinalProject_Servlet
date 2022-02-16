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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/input_form_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_page/login_page_style.css"
          type="text/css">
</head>
<body>
<jsp:include page="/view/parts/header.jsp"/><br/>

<div class="wrapper fadeInDown">
    <div id="formContent">
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon" alt="User Icon"/>
        </div>

        <div class="message_success">
        ${requestScope.localization.getString("login.text.login_success")}<br/>
            <div class="user_message">
                ${sessionScope.login}
            </div>
        </div>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/">${requestScope.localization.getString("main.text.main_page")}</a>
        </div>
    </div>
</div>

</body>
</html>