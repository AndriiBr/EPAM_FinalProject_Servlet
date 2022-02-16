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
<jsp:include page="/view/parts/header.jsp"/><br/>

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
            ${requestScope.localization.getString("edition_list.text.title_single")}<br/>
            <%= title%><br/>
            ${requestScope.localization.getString("edition_list.text.already_exist")}
        <%}%>

        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/cabinet/admin_console/global_edition_list/add_new_edition">${requestScope.localization.getString("edition_list.button.try_again")}</a>
            <br/>
            <br/>
            <a class="underlineHover" href="http://localhost:8080/cabinet/admin_console/global_edition_list">${requestScope.localization.getString("edition_list.link.edition_list")}</a>
        </div>
    </div>
</div>

</body>
</html>
