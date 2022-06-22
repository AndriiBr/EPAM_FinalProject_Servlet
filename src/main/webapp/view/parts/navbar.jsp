<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : sessionScope.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization/navbar/navbarLocale" var="navbar"/>

<div class="container">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/shop/list">
                <img src="${pageContext.request.contextPath}/img/empty_title_placeholder/app-logo.png" alt="Logo"
                     width="45">
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" style="color: white" href="${pageContext.request.contextPath}/shop/list">
                            SilkRoad Shop
                        </a>
                    </li>

                    <c:if test="${not empty sessionScope.user}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <fmt:message key="navbar.cabinet" bundle="${navbar}"/>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <fmt:message key="navbar.user_settings" bundle="${navbar}"/>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/subscriptions">
                                        <fmt:message key="navbar.subscriptions" bundle="${navbar}"/>
                                    </a>
                                </li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li>
                                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/wallet">
                                        <fmt:message key="navbar.wallet" bundle="${navbar}"/>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.user.role eq '3'}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown2" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <fmt:message key="navbar.admin_console" bundle="${navbar}"/>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <fmt:message key="navbar.edition_list" bundle="${navbar}"/>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="#">
                                        <fmt:message key="navbar.user_list" bundle="${navbar}"/>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:if>
                    <%--                    <li class="nav-item">--%>
                    <%--                        <a class="nav-link disabled">Disabled</a>--%>
                    <%--                    </li>--%>
                </ul>

                <form class="d-flex search_form" method="post"
                      onsubmit="executeUrlParameter('search', document.getElementById('search').value)">
                    <input class="form-control me-2" type="text" id="search" name="search"
                           placeholder="<fmt:message key="navbar.search_msg" bundle="${navbar}"/>" aria-label="Search">
                    <button class="btn btn-dark" type="submit">
                        <fmt:message key="navbar.search" bundle="${navbar}"/>
                    </button>
                </form>

                <select class="d-flex mx-1 btn btn-dark" id="language" name="language"
                        onchange="executeUrlParameter('language', this.options[selectedIndex].value)">
                    <option value="ua" ${language == 'ua' ? 'selected' : ''}>UA</option>
                    <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
                </select>

                <ul class="navbar-nav me-2 mb-2 mb-lg-0">
                    <li class="nav-item">
                        <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown3" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                                ${sessionScope.user.login}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/auth/logout">
                                    <fmt:message key="navbar.log-out" bundle="${navbar}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </c:when>
                    <c:otherwise>
                        <form class="login_menu" method="get" action="${pageContext.request.contextPath}/auth/login">
                            <button class="mx-1 btn btn-dark" type="submit">
                                <fmt:message key="navbar.log-in" bundle="${navbar}"/>
                            </button>
                        </form>
                    </c:otherwise>
                    </c:choose>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>

<script src="${pageContext.request.contextPath}/js/addUrlParameter.js"></script>
