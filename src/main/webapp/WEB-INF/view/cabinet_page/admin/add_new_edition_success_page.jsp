<!DOCTYPE html>
<%@ page import="ua.epam.final_project.util.entity.Edition" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    Edition edition = (Edition) session.getAttribute("newEdition");
    String imagePath = edition.getImagePath();
    request.setAttribute("imagePath", imagePath);
    request.setAttribute("titleImagePath", edition.getImagePath());
    request.setAttribute("title", edition.getTitle());
    request.setAttribute("price", edition.getPrice());
%>

<div class="wrapper fadeInDown">
    <div id="formContent">
        <div class="fadeIn first">
            <img src="${requestScope.imagePath}" id="icon" width="150" height="200" alt="Edition Icon"
                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>
        </div>

        <h3>Edition:</h3>
        ${title}<br/>
        <h3>was successfully created</h3>
        Price: ${price}

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/edition_list/add_new_edition">Add another one</a>
            <br/>
            <br/>
            <a class="underlineHover" href="http://localhost:8080/edition_list">Go back to edition list page</a>
        </div>
    </div>
</div>

</body>
</html>
