<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Edition list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_background/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cabinet_page/admin/edition_list_page_style.css"
          type="text/css">
</head>
<body>

<c:set var="editions" value="${requestScope.editionList}"/>

<a href="http://localhost:8080/add_new_edition">Додати видання</a><br/>

<table border="1">
    <caption>Список видань</caption>
    <tr>
        <th id="title">Назва</th>
        <th id="title_image">Обкладинка</th>
        <th id="price">Ціна</th>
        <th id="delete_edition">Видалити</th>
    </tr>
    <c:forEach items="${editions}" var="edition">
        <c:set var="title" value="${edition.title}"/>
        <c:set var="title_image" value="${edition.imagePath}"/>
        <c:set var="price" value="${edition.price}"/>

        <tr>
            <td>${title}</td>
            <td>
                <c:choose>
                    <c:when test="${title_image == 'no image'}">
                        <img src="${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg" width="150" height="200" alt="empty_placeholder">
                    </c:when>
                    <c:otherwise>
                        <img src="${title_image}" width="150" height="200" alt="${title}">
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${price}</td>
            <td>
                <button onclick="document.getElementById('${title}').style.display='block'">Delete edition</button>

                <div id="${title}" class="modal">
                    <span onclick="document.getElementById('${title}').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content" action="${pageContext.request.contextPath}/cabinet/delete_edition" method="post">
                        <div class="container">
                            <h1>${title}</h1>
                            <c:choose>
                                <c:when test="${title_image == 'no image'}">
                                    <img src="${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg" width="150" height="200" alt="empty_placeholder">
                                </c:when>
                                <c:otherwise>
                                    <img src="${title_image}" width="150" height="200" alt="${title}">
                                </c:otherwise>
                            </c:choose>

                            <p>Delete this edition?</p>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('${title}').style.display='none'" class="cancel">Cancel</button>
                                <button type="submit" name="edition_title" value="${title}" class="delete-btn">Delete</button>
                            </div>
                        </div>
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
