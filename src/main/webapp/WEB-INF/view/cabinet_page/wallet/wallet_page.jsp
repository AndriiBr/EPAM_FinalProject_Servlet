<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cabinet_page/wallet_page/wallet_page_style.css"
          type="text/css">
</head>
<body>

<%
    String login = (String) session.getAttribute("login");
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
                    Привіт <%= login%>!
                </div>
            </div>
        </div>

        <!-- Balance bar -->
        <div class="fadeIn third">
            <div class="balance-area">
                <label id="balance">Поточний баланс: </label><br/>
                <div class="fadeIn fourth">
                    <div class="balance-bar">
                        <%= balance%> UAH
                    </div>
                </div>
            </div>
        </div>

        <form method="post">
            <input type="submit" class="fadeIn fifth" value="Поповнити баланс"/>
        </form>


        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/cabinet">Back to personal cabinet</a>
        </div>
    </div>
</div>

</body>
</html>
