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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link href="${pageContext.request.contextPath}/css/error_page/error_404_page_style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/view/parts/header.jsp"/><br/>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="../../img/error_page/unknown_error_placeholder.gif" alt="icon" class="icon2"/>
        </div>

        <!-- Error Message -->
        <a2 class="fadeIn second">
            <br/>
            <br/>
            ${requestScope.localization.getString("error.text.oops")}
            <br/>
            <br/>
            ${requestScope.localization.getString("error.text.unknown_error1")}
            <br/>
            ${requestScope.localization.getString("error.text.unknown_error2")}
            <br/>
            <br/>
        </a2>

        <!-- Remind Password -->
        <div id="formFooter">
            <a class="underlineHover" href="${pageContext.request.contextPath}/">${requestScope.localization.getString("main.text.main_page")}</a>
        </div>

    </div>
</div>
</body>