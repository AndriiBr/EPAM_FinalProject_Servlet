<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/cabinet/edition_list/add_new_edition" enctype="multipart/form-data">
    <input type="text" name="edition-title" placeholder="Edition title"><br/>
    <input type="file" name="file-name"><br/>
    <input type="text" name="price" placeholder="Price"><br/>
    <button>Send</button>
</form>

</body>
</html>
