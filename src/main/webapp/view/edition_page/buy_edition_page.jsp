<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization/locale" var="locale"/>
<fmt:setBundle basename="localization/user/userLocale" var="user"/>


<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Buy edition</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/error_markers_style.css"
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
            <img class="mt-3" src="${requestScope.edition.imagePath}" id="icon" width="180" alt="User Icon"
                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/><br/><br/>
            <div class="wallet-header">
                <c:choose>
                    <c:when test="${language == 'ua'}">
                        <c:out value="${requestScope.edition.titleUa}"/>
                    </c:when>
                    <c:when test="${language == 'en'}">
                        <c:out value="${requestScope.edition.titleEn}"/>
                    </c:when>
                </c:choose>
            </div>
        </div>


        <!-- Price bar -->
        <div class="fadeIn second">
            <div class="balance-area mt-3">
                <label id="price">
                    <fmt:message key="edition.buy.edition_price" bundle="${locale}"/>
                </label><br/>
                <div class="fadeIn second">
                    <div class="price-bar fs-4 py-1">
                        ${requestScope.edition.price}
                    </div>
                </div>
            </div>
        </div>

        <!-- Balance bar -->
        <div class="fadeIn third">
            <div class="balance-area">
                <label id="balance">
                    <fmt:message key="edition.buy.current_balance" bundle="${locale}"/>
                </label><br/>
                <div class="fadeIn third">
                    <div class="balance-bar fs-4 py-1">
                        ${sessionScope.user.balance}
                    </div>
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${requestScope.remainingBalance < 0}">
                <div class="fadeIn fourth">
                    <div class="balance-area">
                        <label id="label-not-enough-balance">
                            <fmt:message key="edition.buy.remaining_balance" bundle="${locale}"/>
                        </label>
                        <div class="fadeIn fourth">
                            <div id="not-enough-balance" class="not-enough-balance fs-4 py-1 mb-3">
                                    ${requestScope.remainingBalance}
                            </div>
                        </div>
                    </div>
                    <div class="error error-text">
                        <fmt:message key="edition.buy.text.no_money" bundle="${locale}"/>
                    </div>
                    <br/>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Remaining balance bar (Enough money) -->
                <div class="fadeIn fourth">
                    <div class="balance-area ">
                        <label id="label-remain-balance">
                            <fmt:message key="edition.buy.remaining_balance" bundle="${locale}"/>
                        </label>
                        <div class="fadeIn fourth">
                            <div id="remain-balance" class="remain-balance-bar fs-4 py-1 mb-3">
                                    ${requestScope.remainingBalance}
                            </div>
                        </div>
                    </div>
                </div>

                <div class="fadeIn fifth">
                    <form method="post">
                        <button class="btn btn-info btn-lg btn-block mb-3" type="submit">
                            <fmt:message key="edition_list.buy" bundle="${locale}"/>
                        </button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>

        <div id="formFooter">
            <div class="fadeIn sixth">
                <c:if test="${(sessionScope.user.role eq '3' or sessionScope.user.role eq '2') and requestScope.remainingBalance < 0}">
                    <div class="d-flex justify-content-center pb-lg-2 user_wallet">
                        <a class="link-info" href="${pageContext.request.contextPath}/user/wallet/top_up">
                            <fmt:message key="user.wallet.top_up" bundle="${user}"/>
                        </a>
                    </div>
                </c:if>
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
