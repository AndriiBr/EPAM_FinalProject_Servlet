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
    <title>Wallet fill up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/input_form_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/error_markers_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cabinet_page/wallet_page/wallet_page_style.css"
          type="text/css">

    <%--    Font CDN Link for Icons--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<jsp:include page="/view/parts/header.jsp"/><br/>

<%
    int balance = (Integer) session.getAttribute("balance");
%>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon"
                 alt="User Icon"/><br/>
            <div class="fadeIn second">
                <div class="wallet-header">
                    ${requestScope.localization.getString("wallet.text.hello")} ${sessionScope.login}!
                </div>
            </div>
        </div>

        <!-- Balance bar -->
        <div class="fadeIn third">
            <div class="balance-area">
                <label id="balance">${requestScope.localization.getString("wallet.text.balance")} </label><br/>
                <div class="fadeIn fourth">
                    <div class="balance-bar">
                        <%= balance%> ${requestScope.localization.getString("wallet.label.uah")}
                    </div>
                </div>
            </div>
        </div>

        <form name="balance_form" method="post">
            <div class="field money">
                <div class="input-area">
                    <label for="money">${requestScope.localization.getString("wallet.button.top_up")}</label><br/>
                    <input type="text" id="money" class="fadeIn third" name="money" placeholder="${requestScope.localization.getString("wallet.label.enter_amount")}">
                    <div class="fadeIn third">
                        <em class="icon fas fa-money-bill"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Field can`t be blank</div>
            </div>

            <!-- Submit button -->
            <input type="submit" class="fadeIn fifth" value="Поповнити"/>
        </form>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/cabinet">${requestScope.localization.getString("wallet.link.cabinet")}</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/wallet_page/functions/money_value_function_module.js"></script>
<script src="${pageContext.request.contextPath}/js/wallet_page/fill_up_wallet_validator.js"></script>

</body>
</html>
