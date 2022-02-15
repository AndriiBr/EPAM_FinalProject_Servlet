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
    <title>Wallet</title>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cabinet_page/wallet_page/wallet_page_style.css"
          type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/><br/>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="${requestScope.edition.imagePath}" id="icon" width="150" height="200" alt="User Icon"
                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/><br/><br/>
            <div class="wallet-header">
                ${requestScope.edition.title}
            </div>
        </div>


        <!-- Price bar -->
        <div class="fadeIn second">
            <div class="balance-area">
                <label id="price">${requestScope.localization.getString("edition_list.label.edition_price")} </label><br/>
                <div class="fadeIn second">
                    <div class="price-bar">
                        ${requestScope.edition.price}
                    </div>
                </div>
            </div>
        </div>

        <!-- Balance bar -->
        <div class="fadeIn third">
            <div class="balance-area">
                <label id="balance">${requestScope.localization.getString("edition_list.label.balance")} </label><br/>
                <div class="fadeIn third">
                    <div class="balance-bar">
                        ${requestScope.user.balance}
                    </div>
                </div>
            </div>
        </div>

        <c:choose>
            <c:when test="${(requestScope.user.balance - requestScope.edition.price) < 0}">
                <div class="fadeIn fourth">
                    <div class="balance-area">
                        <label id="label-not-enough-balance">${requestScope.localization.getString("edition_list.label.remaining")} </label><br/>
                        <div class="fadeIn fourth">
                            <div id="not-enough-balance" class="not-enough-balance">
                                    ${requestScope.user.balance - requestScope.edition.price}
                            </div>
                        </div>
                    </div>
                    <div class="error error-text">${requestScope.localization.getString("edition_list.text.no_money")}</div><br/>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Remaining balance bar (Enough money) -->
                <div class="fadeIn fourth">
                    <div class="balance-area">
                        <label id="label-remain-balance">${requestScope.localization.getString("edition_list.label.remaining")} </label><br/>
                        <div class="fadeIn fourth">
                            <div id="remain-balance" class="remain-balance-bar">
                                    ${requestScope.user.balance - requestScope.edition.price}
                            </div>
                        </div>
                    </div>
                </div>

                <form method="post">
                    <input type="submit" class="fadeIn fifth" value="Buy"/>
                </form>
            </c:otherwise>
        </c:choose>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/cabinet/wallet">${requestScope.localization.getString("edition_list.link.top_up")}</a>
            <br/>
            <br/>
            <a class="underlineHover" href="http://localhost:8080/edition_list">${requestScope.localization.getString("edition_list.link.edition_list")}</a>
        </div>
    </div>
</div>

</body>
</html>
