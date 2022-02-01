<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wallet fill up</title>
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
                        <%= balance%> UAH-H
                    </div>
                </div>
            </div>
        </div>

        <form method="post">
            <div class="field password">
                <div class="input-area">
                    <input type="text" id="money" class="fadeIn third" name="money" placeholder="Enter amount">
                    <div class="fadeIn third">
                        <em class="icon fas fa-lock"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Password can`t be blank</div>
            </div>

            <input type="submit" class="fadeIn fifth" value="Поповнити"/>
        </form>


        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/cabinet">Back to personal cabinet</a>
        </div>
    </div>
</div>


</body>
</html>
