<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_page/login_page_style.css"
          type="text/css">

</head>
<body>

<%
    String login = (String) session.getAttribute("formLogin");
    String email = (String) session.getAttribute("formEmail");
    String errorFlag = (String) session.getAttribute("errorFlag");
%>

<div class="wrapper fadeInDown">
    <div id="formContent">
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon" alt="User Icon"/>
        </div>

        <% switch (errorFlag) {
            case "1": %>
        Password does not match.<br>
        Please try again.
        <%
                break;
            case "2":
        %>
        Username<br/>
        <%= login%><br/>
        already exist
        <% break;
            case "3": %>
        Email address<br/>
        <%= email%><br/>
        already exist
        <%}%>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/new_account">Go back to registration form</a>
        </div>
    </div>
</div>
</body>
</html>