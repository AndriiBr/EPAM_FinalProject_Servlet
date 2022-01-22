<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/loginPage/login_page_style.css"
          type="text/css">
</head>
<body>

<%
    String name = request.getParameter("username");
    request.setAttribute("user", name);
%>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <div class="fadeIn first">
            <img src="../../../img/loginPage/login_screen_user_logo.png" id="icon" alt="User Icon"/>
        </div>

        <h3>You are successfully logged in - ${user}</h3>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/">Go back to main page</a>
        </div>
    </div>
</div>

</body>
</html>