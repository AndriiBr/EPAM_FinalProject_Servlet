<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_background/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_page/login_page_style.css"
          type="text/css">
</head>
<body>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <div class="fadeIn first">
            <img src="../../../img/login_page/login_screen_user_logo.png" id="icon" alt="User Icon"/>
        </div>

        <h3>Wrong login or password</h3>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/login">Try again</a>
            <br/>
            <br/>
            <a class="underlineHover" href="http://localhost:8080/">Go to main page</a>
        </div>
    </div>
</div>

</body>
</html>