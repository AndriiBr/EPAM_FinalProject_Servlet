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
    String title = (String) session.getAttribute("editionTitle");
    String errorFlag = (String) session.getAttribute("editionErrorFlag");
%>

<div class="wrapper fadeInDown">
    <div id="formContent">
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon" alt="User Icon"/>
        </div>

        <% if (errorFlag.equals("1")) {%>
            Title<br/>
            <%= title%><br/>
            already exist
        <%}%>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/cabinet/admin_console/global_edition_list/add_new_edition">Try again</a>
            <br/>
            <br/>
            <a class="underlineHover" href="http://localhost:8080/cabinet/admin_console/global_edition_list">Go back to edition list</a>
        </div>
    </div>
</div>

</body>
</html>
