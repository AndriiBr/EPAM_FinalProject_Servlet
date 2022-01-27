<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/add_new_edition" enctype="multipart/form-data">
    <input type="text" name="edition-title" placeholder="Edition title">
    <input type="file" name="file-name">
    <input type="text" name="price" placeholder="Price">
    <button>Send</button>
</form>

</body>
</html>
