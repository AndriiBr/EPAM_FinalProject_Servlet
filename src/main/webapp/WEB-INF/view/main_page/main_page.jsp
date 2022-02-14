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
    <title>Main page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main_page/main_page_style.css" type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/><br/>

<a href="http://localhost:8080/about">${requestScope.localization.getString("main.link.about_us")}</a><br/>
<a href="http://localhost:8080/edition_list">${requestScope.localization.getString("main.link.edition_list")}</a><br/>

<% if (session.getAttribute("login") != null) { %>
<a href="http://localhost:8080/cabinet">${sessionScope.login}: Особистий кабінет</a><br/>
<a href="http://localhost:8080/logout">${requestScope.localization.getString("main.link.logout")}</a><br/>
<% } else { %>
<a href="http://localhost:8080/login">${requestScope.localization.getString("main.link.login")}</a><br/>
<% } %>

</body>
</html>