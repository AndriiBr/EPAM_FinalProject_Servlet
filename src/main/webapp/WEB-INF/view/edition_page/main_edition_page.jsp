<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main edition list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cabinet_page/admin/edition_list_page_style.css"
          type="text/css">
</head>
<body>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <th id="title">Edition Title</th>
        <th id="title_image">Image Path</th>
        <th id="genres">Genres</th>
        <th id="price">Price</th>
        <th id="buy">Buy</th>
    </tr>

    <c:forEach var="edition" items="${requestScope.editionList}">
        <tr>
            <td>${edition.title}</td>
            <td>
                <img src="${edition.imagePath}" alt="${edition.title}" width="150" height="200"
                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>
            </td>
            <td>
                <c:forEach items="${edition.genres}" var="genre">
                    ${genre}<br/>
                </c:forEach>
            </td>
            <td>${edition.price}</td>
            <%--Buy button--%>
            <td>
                <button onclick="document.getElementById('${edition.title}').style.display='block'">Buy</button>

                <div id="${edition.title}" class="modal">
                    <span onclick="document.getElementById('${edition.title}').style.display='none'" class="close" title="Close Modal">×</span>
                    <form class="modal-content" action="${pageContext.request.contextPath}/cabinet/edition_list/buy_edition" method="post">
                        <div class="container">
                            <h1>${edition.title}</h1>
                            <img src="${edition.imagePath}" alt="${edition.title}" width="150" height="200"
                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>

                            <p>Buy this edition?</p>

                            <div class="clearfix">
                                <button type="button" onclick="document.getElementById('${edition.title}').style.display='none'" class="cancel">Cancel</button>
                                <button type="submit" name="buy_edition" value="${edition.title}" class="buy-btn">Buy</button>
                            </div>
                        </div>
                    </form>
                </div>
            </td>

        </tr>
    </c:forEach>
</table>

<%--For displaying Previous link except for the 1st page --%>
<c:if test="${requestScope.currentPage != 1}">
    <td><a href="http://localhost:8080/main_edition_list?page=${requestScope.currentPage - 1}">Previous</a></td>
</c:if>

<%--For displaying Page numbers.
The when condition does not display a link for the current page--%>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="http://localhost:8080/main_edition_list?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
<c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
    <td><a href="http://localhost:8080/main_edition_list?page=${requestScope.currentPage + 1}">Next</a></td>
</c:if>

</body>
</html>
