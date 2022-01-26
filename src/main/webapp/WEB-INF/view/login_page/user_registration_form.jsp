<!DOCTYPE html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_background/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_page/login_page_style.css"
          type="text/css">
    <%--    Font CDN Link for Icons--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <!-- Tabs Titles -->

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="../../../img/login_page/login_screen_user_logo.png" id="icon" alt="User Icon"/>
            <legend class="registration-header">Let's create your new account</legend>
            <br/>
        </div>

        <!-- Login Form -->
        <form class="form-horizontal" method="POST">
            <!-- Login -->
            <div class="field login">
                <div class="input-area">
                    <input type="text" id="login" class="fadeIn second" name="login" placeholder="Username">
                    <div class="fadeIn second">
                        <em class="icon fas fa-user"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Login can`t be blank</div>
            </div>

            <!-- Email -->
            <div class="field email">
                <div class="input-area">
                    <input type="text" id="email" name="email" placeholder="Email" class="fadeIn third">
                    <div class="fadeIn third">
                        <em class="icon fas fa-envelope"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Email can`t be blank</div>
            </div>

            <!-- Password-->
            <div class="field password">
                <div class="input-area">
                    <input type="password" id="password" name="password" placeholder="Password" class="fadeIn fourth">
                    <div class="fadeIn fourth">
                        <em class="icon fas fa-lock"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Password can`t be blank</div>
            </div>

            <!-- Password-confirm -->
            <div class="field password_confirm">
                <div class="input-area">
                    <input type="password" id="password_confirm" name="password_confirm" placeholder="Confirm Password"
                           class="fadeIn fifth">
                    <div class="fadeIn fourth">
                        <em class="icon fas fa-lock"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Password can`t be blank</div>
            </div>

            <!-- Button -->
            <input type="submit" class="fadeIn sixth" value="Create account">
        </form>

        <!-- Remind Password -->
        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/">I've changed my mind</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/login_page/functions/validation_function_module.js"></script>
<script src="${pageContext.request.contextPath}/js/login_page/user_registration_form_validator.js"></script>
</body>
