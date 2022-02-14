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
    <title>Login page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/input_form_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/error_markers_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_page/login_page_style.css"
          type="text/css">

    <%--    Font CDN Link for Icons--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/><br/>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon"
                 alt="User Icon"/>
        </div>

        <!-- Login Form -->
        <form name="login_form" class="log-in" method="post">
            <!-- Login -->
            <div class="field login">
                <div class="input-area">
                    <input type="text" id="login" class="fadeIn second" name="login"
                           placeholder="${requestScope.localization.getString("login.label.username")}">
                    <div class="fadeIn second">
                        <em class="icon fas fa-user"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Login can`t be blank</div>
            </div>

            <!-- Password -->
            <div class="field password">
                <div class="input-area">
                    <input type="password" id="password" class="fadeIn third" name="password"
                           placeholder="${requestScope.localization.getString("login.label.password")}">
                    <div class="fadeIn third">
                        <em class="icon fas fa-lock"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Password can`t be blank</div>
            </div>

            <input type="submit" class="fadeIn fourth"
                   value="${requestScope.localization.getString("login.button.login")}">
        </form>

        <!-- Remind Password -->
        <div id="formFooter">
            <a class="underlineHover"
               href="http://localhost:8080/new_account">${requestScope.localization.getString("login.link.create_new_account")}</a>
            <br/>
            <br/>
            <a class="underlineHover"
               href="http://localhost:8080/forgot">${requestScope.localization.getString("login.link.forgot_password")}</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/login_page/functions/validation_function_module.js"></script>
<script src="${pageContext.request.contextPath}/js/login_page/login_page-input_validator.js"></script>
</body>
</html>