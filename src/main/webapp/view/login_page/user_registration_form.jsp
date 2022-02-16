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
    <title>Title</title>
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

    <%-- Font CDN Link for Icons--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<jsp:include page="/view/parts/header.jsp"/><br/>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon" alt="User Icon"/>
            <legend class="registration-header">${requestScope.localization.getString("login.text.create_account_message")}</legend>
            <br/>
        </div>

        <!-- Login Form -->
        <form name="registration_form" class="form-horizontal" method="POST">
            <!-- Login -->
            <div class="field login">
                <div class="input-area">
                    <input type="text" id="login" class="fadeIn second" name="login" placeholder="${requestScope.localization.getString("login.label.username")}">
                    <div class="fadeIn second">
                        <em class="icon fas fa-user"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Login can`t be blank</div>
            </div>

            <!-- Email -->
            <div class="field email">
                <div class="input-area">
                    <input type="text" id="email" name="email" class="fadeIn third" placeholder="${requestScope.localization.getString("login.label.email")}">
                    <div class="fadeIn third">
                        <em class="icon fas fa-envelope"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Email can`t be blank</div>
            </div>

            <!-- Password-->
            <div class="field password">
                <div class="input-area">
                    <input type="password" id="password" name="password" class="fadeIn fourth" placeholder="${requestScope.localization.getString("login.label.password")}">
                    <div class="fadeIn fourth">
                        <em class="icon fas fa-lock"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Password can`t be blank</div>
            </div>

            <!-- Password-confirm -->
            <div class="field password_confirm">
                <div class="input-area">
                    <input type="password" id="password_confirm" name="password_confirm" placeholder="${requestScope.localization.getString("login.label.confirm_password")}"
                           class="fadeIn fifth">
                    <div class="fadeIn fourth">
                        <em class="icon fas fa-lock"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Password can`t be blank</div>
            </div>

            <!-- Button -->
            <input type="submit" class="fadeIn sixth" value="${requestScope.localization.getString("login.button.create_account")}">
        </form>

        <!-- Remind Password -->
        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/">${requestScope.localization.getString("login.link.i_changed_my-mind")}</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/login_page/functions/validation_function_module.js"></script>
<script src="${pageContext.request.contextPath}/js/login_page/user_registration_form_validator.js"></script>
</body>
</html>
