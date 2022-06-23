<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization/locale" var="locale"/>

<!DOCTYPE html>
<html lang="${language}">
<head>

    <title>New Edition</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/error_markers_style.css"
          type="text/css">
</head>

<body scroll="no" style="overflow: hidden">
<jsp:include page="/view/parts/navbar.jsp"/>

<div class="wrapper fadeInDown mt-5 pt-4">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img class="mt-3"
                 src="${pageContext.request.contextPath}/img/empty_title_placeholder/Add_new_edition_logo.png" id="icon"
                 alt="User Icon" width="180"/>
            <legend class="registration-header">
                <fmt:message key="edition.add" bundle="${locale}"/>
            </legend>
        </div>

        <%--New edition form--%>
        <form name="new_edition_form" class="form-group" method="post"
              action="${pageContext.request.contextPath}/admin/edition/new-edition"
              enctype="multipart/form-data">

            <!-- Title_En Form -->
            <div class="fadeIn second">
                <div class="d-flex justify-content-center field titleEn">
                    <div class="col-10">
                        <div class="input-area">
                            <input type="text" id="title_en" class="form-control" name="title_en"
                                   placeholder="<fmt:message key="edition.title_en" bundle="${locale}"/>">
                        </div>
                        <div class="error error-text"></div>
                    </div>
                </div>
            </div>

            <!-- Title_Ua Form -->
            <div class="fadeIn second">
                <div class="d-flex justify-content-center field titleUa">
                    <div class="col-10">
                        <div class="input-area">
                            <input type="text" id="title_ua" class="form-control" name="title_ua"
                                   placeholder="<fmt:message key="edition.title_ua" bundle="${locale}"/>">
                        </div>
                        <div class="error error-text"></div>
                    </div>
                </div>
            </div>

            <!-- Text_En Form -->
            <div class="fadeIn third">
                <div class="d-flex justify-content-center field textEn">
                    <div class="col-10">
                        <div class="input-area">
                            <input type="text" id="text_en" class="form-control" name="text_en"
                                   placeholder="<fmt:message key="edition.text_en" bundle="${locale}"/>">
                        </div>
                        <div class="error error-text"></div>
                    </div>
                </div>
            </div>

            <!-- Text_Ua Form -->
            <div class="fadeIn third">
                <div class="d-flex justify-content-center field textUa">
                    <div class="col-10">
                        <div class="input-area">
                            <input type="text" id="text_ua" class="form-control" name="text_ua"
                                   placeholder="<fmt:message key="edition.text_ua" bundle="${locale}"/>">
                        </div>
                        <div class="error error-text"></div>
                    </div>
                </div>
            </div>


            <!-- Price Form -->
            <div class="fadeIn fourth">
                <div class="d-flex justify-content-center field price">
                    <div class="col-10">
                        <div class="input-area">
                            <input type="text" id="price" class="form-control" name="price"
                                   placeholder="<fmt:message key="edition.price" bundle="${locale}"/>">
                        </div>
                        <div class="error error-text"></div>
                    </div>
                </div>
            </div>

            <!-- Genre Form -->
            <div class="fadeIn fourth">
                <div class="d-flex justify-content-center field genre">
                    <div class="col-10">
                        <div class="input-area">
                            <select id="genre" class="form-select" name="genre">
                                <option selected
                                        disabled="disabled">
                                    <fmt:message key="edition.genre" bundle="${locale}"/>
                                </option>
                                <c:forEach items="${requestScope.genreList}" var="genre">
                                    <c:choose>
                                        <c:when test="${language == 'ua'}">
                                            <option value="${genre.genreUa}">${genre.genreUa}</option>
                                        </c:when>
                                        <c:when test="${language == 'en'}">
                                            <option value="${genre.genreEn}">${genre.genreEn}</option>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="error error-text"></div>
                    </div>
                </div>
            </div>

            <!-- File Form -->
            <div class="fadeIn fifth">
                <div class="d-flex justify-content-center field file">
                    <div class="col-10">
                        <div class="input-area">
                            <input type="file" class="form-control" id="file-name" name="file-name">
                        </div>
                    </div>
                </div>
            </div>

            <div class="fadeIn sixth">
                <div class="d-flex justify-content-center pt-1 mb-4 mt-4">
                    <button type="submit" class="btn btn-info btn-lg btn-block"
                            value="${requestScope.localization.getString("edition_list.link.add_edition")}">
                        <fmt:message key="edition.add" bundle="${locale}"/>
                    </button>
                </div>
            </div>
        </form>

        <!-- Go back to edition page -->
        <div id="formFooter">
            <a class="underlineHover"
               href="${pageContext.request.contextPath}/admin/edition">
                <fmt:message key="edition.back_to_list" bundle="${locale}"/>
            </a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/edition_page/functions/validation_function_edition_input_module.js"></script>
<script src="${pageContext.request.contextPath}/js/edition_page/add_new_edition_input_validator.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
        crossorigin="anonymous"></script>
</body>
</html>
