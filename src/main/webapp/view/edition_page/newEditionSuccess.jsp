<%@ page import="ua.epam.final_project.entity.Edition" %>
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

<div class="wrapper fadeInDown">
    <div id="formContent">
        <div class="fadeIn first">
            <img src="${sessionScope.edition.imagePath}" id="icon" width="150" height="200" alt="Edition Icon"
                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>
        </div>

        <h3>${requestScope.localization.getString("edition_list.text.title_single")}</h3>
        <c:choose>
            <c:when test="${language == 'ua'}">${sessionScope.edition.titleUa}</c:when>
            <c:when test="${language == 'en'}">${sessionScope.edition.titleEn}</c:when>
        </c:choose>
        <h3>${requestScope.localization.getString("edition_list.text.operation_success")}</h3>
        ${requestScope.localization.getString("edition_list.text.price")}: ${sessionScope.edition.price}

        <div id="formFooter">
            <a class="underlineHover" href="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list">${requestScope.localization.getString("edition_list.link.edition_list")}</a>
        </div>
    </div>
</div>

</body>
</html>
