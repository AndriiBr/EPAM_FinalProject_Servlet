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
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/frame_structure_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/fade_in_animation.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/input_form_style.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/error_markers_style.css"
          type="text/css">

    <%-- Font CDN Link for Icons--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<jsp:include page="/WEB-INF/view/parts/header.jsp"/><br/>

<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/empty_title_placeholder/Add_new_edition_logo.png" id="icon"
                 alt="User Icon"/>
            <legend class="registration-header">${requestScope.localization.getString("edition_list.text.edit_text3")}</legend>
            <br/>
        </div>

        <%--New edition form--%>
        <form class="form-horizontal" method="post"
              action="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list/add_new_edition"
              enctype="multipart/form-data">
            <!-- Title Form -->
            <%-- <div class="field login"> --%>
            <div class="field title">
                <div class="input-area">
                    <input type="text" id="title" class="fadeIn second" name="title" placeholder="${requestScope.localization.getString("edition_list.text.title")}"><br/>
                    <div class="fadeIn second">
                        <em class="icon fas fa-book"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Title can`t be blank</div>
            </div>


            <!-- Price Form -->
            <div class="field price">
                <div class="input-area">
                    <input type="text" id="price" class="fadeIn third" name="price" placeholder="${requestScope.localization.getString("edition_list.text.price")}"><br/>
                    <div class="fadeIn third">
                        <em class="icon fas fa-money-bill"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Price can`t be blank</div>
            </div>

            <!-- Genre Form -->
            <div class="field genre">
                <div class="input-area">
                    <label for="genre"></label>
                    <select id="genre" class="fadeIn fourth" name="genre">
                        <option selected disabled="disabled">${requestScope.localization.getString("edition_list.text.genre")}</option>
                        <c:forEach items="${requestScope.genresList}" var="genre">
                            <option value="${genre}">${genre}</option>
                        </c:forEach>
                    </select><br/>
                    <div class="fadeIn fourth">
                        <em class="icon fas fa-atlas"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Please select a genre</div>
            </div>

            <!-- File Form -->
            <div class="field file">
                <div class="input-area">
                    <input type="file" class="fadeIn fifth" name="file-name" placeholder="${requestScope.localization.getString("edition_list.text.image")}"><br/>
                </div>
            </div>

            <input type="submit" class="fadeIn sixth" value="${requestScope.localization.getString("edition_list.link.add_edition")}">
        </form>

        <!-- Go back to edition page -->
        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/edition_list">${requestScope.localization.getString("edition_list.link.edition_list")}</a>
        </div>

    </div>
</div>

<script src="${pageContext.request.contextPath}/js/edition_page/functions/validation_function_edition_input_module.js"></script>
<script src="${pageContext.request.contextPath}/js/edition_page/add_new_edition_input_validator.js"></script>

</body>
</html>
