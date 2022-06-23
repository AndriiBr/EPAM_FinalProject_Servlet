<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session" />

<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization/error/errorLocale" var="error" />
<fmt:setBundle basename="localization/user/userLocale" var="user"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title>Unknown Exception</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">

</head>
<body >
<jsp:include page="/view/parts/navbar.jsp"/>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first mt-5 pt-3">
            <img src="../../img/error_page/unknown_error_placeholder.gif" alt="icon" width="210"/>
        </div>

        <!-- Error Message -->
        <a class="fadeIn second">
            <p class="text-center mb-2 mt-4 fw-bold fs-3"><fmt:message key="error.message.oops" bundle="${error}"/></p>
            <p class="text-center mb-0 fs-5"><fmt:message key="error.message.something_go_wrong" bundle="${error}"/></p>
            <p class="text-center mb-4 fs-5"><fmt:message key="error.message.working_on_it" bundle="${error}"/></p>
        </a>

        <!-- Main Page -->
        <div id="formFooter">
            <div class="fadeIn third">
                <div class="d-flex justify-content-center pb-lg-2 user_wallet">
                    <a class="link-info" href="${pageContext.request.contextPath}/shop/list">
                        <fmt:message key="user.main_page" bundle="${user}"/>
                    </a>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
        crossorigin="anonymous"></script>
</body>
</html>