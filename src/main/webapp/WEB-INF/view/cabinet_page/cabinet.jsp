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
    <title>Cabinet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
</head>
<body>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/><br/>

<%
    String role = (String) session.getAttribute("role");
%>

<%
    if (role.equals("1") || role.equals("2")) {%>
<h1>${requestScope.localization.getString("cabinet.text.personal_cabinet")}: ${sessionScope.login}</h1>

<a href="http://localhost:8080/cabinet/user_settings">${requestScope.localization.getString("cabinet.link.user_settings")}</a><br/>
<a href="http://localhost:8080/cabinet/subscription_list">${requestScope.localization.getString("cabinet.link.subscriptions")}</a><br/>
<a href="http://localhost:8080/cabinet/wallet">${requestScope.localization.getString("cabinet.link.wallet")}</a><br/>

<%}
    if (role.equals("1")) {%>
<a href="http://localhost:8080/cabinet/admin_console">${requestScope.localization.getString("cabinet.link.admin_console")}</a><br/>
<%}%>

</body>
</html>
