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
            <a class="navbar-brand" href="#">
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
                        <a class="nav-link active" aria-current="page" href="#">
                            <fmt:message key="navbar.main" bundle="${navbar}"/>
                        </a>
                    </li>
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
                                <a class="dropdown-item" href="#">
                                    <fmt:message key="navbar.subscriptions" bundle="${navbar}"/>
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="dropdown-item" href="#">
                                    <fmt:message key="navbar.wallet" bundle="${navbar}"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled">Disabled</a>
                    </li>
                </ul>

                <form class="d-flex" role="search">
                    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-dark" type="submit">Search</button>
                </form>

                <%--                <form class="d-flex" name="lang_switch" method="get">--%>
                <%--                    <select class="btn btn-dark" id="language" name="language" onchange="submit()">--%>
                <%--                        <option value="ua" ${language == 'ua' ? 'selected' : ''}>UA</option>--%>
                <%--                        <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>--%>
                <%--                    </select>--%>
                <%--                </form>--%>

                <select class="d-flex btn btn-dark" id="language" name="language"
                        onchange="changeLang(this.options[selectedIndex].value)">
                    <option value="ua" ${language == 'ua' ? 'selected' : ''}>UA</option>
                    <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
                </select>


                <ul class="navbar-nav me-2 mb-2 mb-lg-0">
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <a class="nav-link" href="http://localhost:8080/logout">
                                    <fmt:message key="navbar.log-out" bundle="${navbar}"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <form class="login_menu" method="post" action="login">
                                    <input type="hidden" name="command" value="login"/>
                                    <button class="btn btn-dark" type="submit">
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

<script>
    const changeLang = (languageCode) => {
        document.documentElement.setAttribute("lang", languageCode);
        // window.location.href += "?language=" + languageCode;
        window.location.href = URL_add_parameter(window.location.href, "language", languageCode);
        // location.reload();
    };

    function URL_add_parameter(url, param, value) {
        const hash = {};
        const parser = document.createElement('a');

        parser.href = url;

        const parameters = parser.search.split(/\?|&/);

        for (let i = 0; i < parameters.length; i++) {
            if (!parameters[i])
                continue;

            const ary = parameters[i].split('=');
            hash[ary[0]] = ary[1];
        }

        hash[param] = value;

        const list = [];
        Object.keys(hash).forEach(function (key) {
            list.push(key + '=' + hash[key]);
        });

        parser.search = '?' + list.join('&');
        return parser.href;
    }
</script>
