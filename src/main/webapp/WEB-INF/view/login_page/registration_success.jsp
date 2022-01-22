<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/login_page/login_page_style.css"
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
            <img src="../../../img/login_page/login_screen_user_logo.png" id="icon" alt="User Icon"/>
        </div>

        <h3>Account for ${user} was successfully created</h3>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/">Go back to main page</a>
        </div>
    </div>
</div>

</body>
</html>