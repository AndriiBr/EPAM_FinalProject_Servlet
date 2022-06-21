<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<%-- Deprecated --%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization/locale" var="locale"/>
<fmt:setBundle basename="localization/authentication/authLocale" var="auth"/>


<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Login page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_page/auth_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/error_markers_style.css"
          type="text/css">

</head>
<body scroll="no" style="overflow: hidden">
<jsp:include page="/view/parts/navbar.jsp"/>

<section class="vh-100">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-6 text-black">
                <div class="d-flex align-items-center justify-content-center mt-5 pt-5 px-5 ms-xl-4 mb-0">
                    <img class="mt-0" src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png"
                         alt="Logo"
                         width="140">
                </div>

                <div class="d-flex align-items-center justify-content-center h-custom-4 px-5 ms-xl-4 mt-2 mb-5 pb-5 pt-2 pt-xl-0 mt-xl-n5">

                    <form style="width: 25rem;" name="login_form" class="log-in" method="post" action="">

                        <h3 class="d-flex justify-content-center fw-normal mb-3 pb-3" style="letter-spacing: 1px;">
                            <fmt:message key="auth.login.form" bundle="${auth}"/>
                        </h3>

                        <div class="field login">
                            <div class="input-area">
                                <div class="form-outline mb-4">
                                    <input type="text" id="login" name="login" class="form-control form-control-lg"
                                           placeholder="<fmt:message key="auth.username" bundle="${auth}"/>"/>
                                    <div class="error error-text"></div>
                                </div>
                            </div>
                        </div>

                        <div class="field password">
                            <div class="input-area">
                                <div class="form-outline mb-4">
                                    <input type="password" id="password" class="form-control form-control-lg"
                                           name="password"
                                           placeholder="<fmt:message key="auth.password" bundle="${auth}"/>"/>
                                    <div class="error error-text"></div>
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-center pt-1 mb-4">
                            <button class="btn btn-info btn-lg btn-block" type="submit">
                                <fmt:message key="auth.sign_in" bundle="${auth}"/>
                            </button>
                        </div>

                        <p class="d-flex justify-content-center small mb-4 pb-lg-2">
                            <a class="text-muted" href="/show_error/error">
                                <fmt:message key="auth.forgot" bundle="${auth}"/>
                            </a>
                        </p>
                        <p class="d-flex justify-content-center">
                            <fmt:message key="auth.no_account" bundle="${auth}"/>
                            <a href="${pageContext.request.contextPath}/auth/registration" class="mx-2 link-info">
                                <fmt:message key="auth.registration" bundle="${auth}"/>
                            </a>
                        </p>

                    </form>
                </div>

            </div>
            <div class="col-sm-6 px-0 d-none d-sm-block">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/img3.webp"
                     alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
            </div>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/js/login_page/functions/validation_function_module.js"></script>
<script src="${pageContext.request.contextPath}/js/login_page/login_page-input_validator.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
        crossorigin="anonymous"></script>
</body>
</html>