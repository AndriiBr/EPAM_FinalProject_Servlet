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
            <div class="blank">1</div>
            <div class="err">4</div>
            <img src="../../img/error_page/spinning_dog_format_6_to_4p1.gif" alt="icon" class="icon"/>
            <div class="err2">4</div>
        </div>

        <!-- Error Message -->
        <a2 class="fadeIn second">
            <br/>
            <br/>
            ${requestScope.localization.getString("error.text.oops")}
            <br/>
            <br/>
            ${requestScope.localization.getString("error.text.message")}
            <br/>
            ${requestScope.localization.getString("error.text.page_not_exist")}
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