<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wallet</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_background/background.css"
          type="text/css">
</head>
<body>

<%
    String login = (String) session.getAttribute("login");
%>

<h1>Персональний гаманець користувача: <%= login%></h1>

</body>
</html>
