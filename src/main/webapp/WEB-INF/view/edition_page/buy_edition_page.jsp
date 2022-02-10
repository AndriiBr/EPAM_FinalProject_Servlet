<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
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
                <label id="price">Edition price: </label><br/>
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
                <label id="balance">Balance: </label><br/>
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
                        <label id="label-not-enough-balance">Remaining balance: </label><br/>
                        <div class="fadeIn fourth">
                            <div id="not-enough-balance" class="not-enough-balance">
                                    ${requestScope.user.balance - requestScope.edition.price}
                            </div>
                        </div>
                    </div>
                    <div class="error error-text">Not enough money</div><br/>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Remaining balance bar (Enough money) -->
                <div class="fadeIn fourth">
                    <div class="balance-area">
                        <label id="label-remain-balance">Remaining balance: </label><br/>
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
            <a class="underlineHover" href="http://localhost:8080/cabinet/wallet">Top up the balance</a>
            <br/>
            <br/>
            <a class="underlineHover" href="http://localhost:8080/edition_list">Back to edition page</a>
        </div>
    </div>
</div>

</body>
</html>
