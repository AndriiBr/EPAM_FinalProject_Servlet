<head>
    <title>Login page</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/loginPage/login_page_style.css"
          type="text/css">
</head>

<body>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <!-- Tabs Titles -->

        <!-- Icon -->
        <div class="fadeIn first">
            <img src="../../../img/loginPage/login_screen_user_logo.png" id="icon" alt="User Icon"/>
        </div>

        <!-- Login Form -->
        <form>
            <input type="text" id="login" class="fadeIn second" name="login" placeholder="login">
            <input type="text" id="password" class="fadeIn third" name="login" placeholder="password">
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
</body>