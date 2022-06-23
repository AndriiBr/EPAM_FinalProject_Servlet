<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization/user/userLocale" var="user"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Wallet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cabinet_page/wallet_page/wallet_page_style.css"
          type="text/css">
</head>

<body>
<jsp:include page="/view/parts/navbar.jsp"/>


<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon"
                 alt="User Icon" width="180"/>
            <div class="fadeIn second">
                <div class="wallet-header mb-2 fs-4">
                    <fmt:message key="user.hello" bundle="${user}"/> ${sessionScope.user.login}!
                </div>
            </div>
        </div>

        <!-- Balance bar -->
        <div class="fadeIn third">
            <div class="balance-area">
                <label class="d-flex justify-content-center fw-bold" id="balance">
                    <fmt:message key="user.wallet.balance" bundle="${user}"/>
                </label>
                <div class="fadeIn fourth mb-4">
                    <div class="balance-bar">
                        ${sessionScope.user.balance} <fmt:message key="user.wallet.currency.uah" bundle="${user}"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="fadeIn fourth">
            <c:if test="${sessionScope.user.role eq '3' or sessionScope.user.role eq '2'}">
                <div class="faiIn fifth d-flex justify-content-center pt-1 mb-4">
                    <a class="btn btn-info btn-lg btn-block"
                       href="${pageContext.request.contextPath}/user/wallet/top_up">
                        <fmt:message key="user.wallet.top_up" bundle="${user}"/>
                    </a>
                </div>
            </c:if>
        </div>

        <div id="formFooter">
            <div class="fadeIn fifth">
                <a class="underlineHover" href="${pageContext.request.contextPath}/shop/list">
                    <fmt:message key="user.main_page" bundle="${user}"/>
                </a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
        crossorigin="anonymous"></script>
</body>
</html>
