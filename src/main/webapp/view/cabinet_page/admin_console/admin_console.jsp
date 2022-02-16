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
    <title>Admin console</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
</head>
<body>
<jsp:include page="/view/parts/header.jsp"/><br/>

<h1>${requestScope.localization.getString("admin_console.text.console")}</h1>

<a href="http://localhost:8080/cabinet/admin_console/global_edition_list">${requestScope.localization.getString("admin_console.link.editions")}</a><br/>
<a href="http://localhost:8080/cabinet/admin_console/user_list">${requestScope.localization.getString("admin_console.link.users")}</a><br/>


</body>
</html>
