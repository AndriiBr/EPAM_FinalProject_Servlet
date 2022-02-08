<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
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
<div class="wrapper fadeInDown">
    <div id="formContent">

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="${pageContext.request.contextPath}/img/empty_title_placeholder/Add_new_edition_logo.png" id="icon"
                 alt="User Icon"/>
            <legend class="registration-header">Here you can add a new edition</legend>
            <br/>
        </div>

        <%--New edition form--%>
        <form class="form-horizontal" method="post"
              action="${pageContext.request.contextPath}/edition_list/add_new_edition"
              enctype="multipart/form-data">
            <!-- Title Form -->
            <%-- <div class="field login"> --%>
            <div class="field title">
                <div class="input-area">
                    <input type="text" id="title" class="fadeIn second" name="title" placeholder="Edition title"><br/>
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
                    <input type="text" id="price" class="fadeIn third" name="price" placeholder="Price"><br/>
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
                        <option selected disabled="disabled">Choose a genre</option>
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
                    <input type="file" class="fadeIn fifth" name="file-name" placeholder="Title image"><br/>
                </div>
            </div>

            <input type="submit" class="fadeIn sixth" value="Create edition">
        </form>

        <!-- Go back to edition page -->
        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/edition_list">Go back to edition page</a>
        </div>

    </div>
</div>

<script src="${pageContext.request.contextPath}/js/edition_page/functions/validation_function_edition_input_module.js"></script>
<script src="${pageContext.request.contextPath}/js/edition_page/add_new_edition_input_validator.js"></script>

</body>
</html>
