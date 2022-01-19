<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
        <form class="form-horizontal" action='' method="POST">

            <legend class="registration-header">Let's create your new account</legend>
            <br/>
            <br/>


            <!-- Username -->
            <label class="fadeIn second" for="username">Username</label>
            <input type="text" id="username" name="username" placeholder="" class="fadeIn second">
            <%--<p class="help-block">Username can contain any letters or numbers, without spaces</p>--%>

            <!-- E-mail -->
            <br/>
            <label class="fadeIn third" for="email">E-mail</label><br/>
            <input type="text" id="email" name="email" placeholder="" class="fadeIn third">
            <%--<p class="help-block">Please provide your E-mail</p>--%>
            <br/>
            <br/>


            <!-- Password-->
            <label class="fadeIn fourth" for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="" class="fadeIn fourth">
            <%--<p class="help-block">Password should be at least 4 characters</p>--%>

            <!-- Password -->
            <label class="fadeIn fifth" for="password_confirm">Password (Confirm)</label>
            <input type="password" id="password_confirm" name="password_confirm" placeholder=""
                   class="fadeIn fifth">
            <%--<p class="help-block">Please confirm password</p>--%>

            <!-- Button -->
            <input type="submit" class="fadeIn sixth" value="Create account">
        </form>

        <!-- Remind Password -->
        <div id="formFooter">
            <a class="underlineHover" href="http://localhost:8080/">I've changed my mind</a>
        </div>

    </div>
</div>
</body>
</html>
