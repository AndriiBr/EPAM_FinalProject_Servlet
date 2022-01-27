<!DOCTYPE html>
<head>
    <title>Login page</title>
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
            <img src="${pageContext.request.contextPath}/img/login_page/login_screen_user_logo.png" id="icon"
                 alt="User Icon"/>
            <img src="http://localhost:8080/images/test.jpg" id="icon2" alt="User Icon"/>
        </div>

        <!-- Login Form -->
        <form class="log-in" method="post">
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

            <!-- Password -->
            <div class="field password">
                <div class="input-area">
                    <input type="password" id="password" class="fadeIn third" name="password" placeholder="Password">
                    <div class="fadeIn third">
                        <em class="icon fas fa-lock"></em>
                    </div>
                    <em class="error icon-error fas fa-exclamation-circle"></em>
                </div>
                <div class="error error-text">Password can`t be blank</div>
            </div>

            <input type="submit" class="fadeIn fourth" value="Log In">
        </form>

        <!-- Remind Password -->
        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/new_account">Create new account</a>
            <br/>
            <br/>
            <a class="underlineHover" href="http://localhost:8080/forgot">Forgot Password?</a>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/login_page/functions/validation_function_module.js"></script>
<script src="${pageContext.request.contextPath}/js/login_page/login_page-input_validator.js"></script>
</body>