<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main edition list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edition_page/edition_list_page_style.css"
          type="text/css">
</head>
<body>

<%
    String role = "";
    if (session.getAttribute("role") != null) {
        role = (String) session.getAttribute("role");
    }
%>

<% if (role.equals("1")) {%>
<a href="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list/add_new_edition">Додати видання</a><br/>
<%}%>


<table border="1" cellpadding="5" cellspacing="3">
    <tr>
        <th id="title">Edition Title</th>
        <th id="title_image">Image Path</th>
        <th id="genres">
            <form action="${pageContext.request.contextPath}/edition_list" method="get">
                <p><label for="genre">Genre</label><br/>
                    <select id="genre" name="genre" onchange="this.form.submit()">
                        <c:forEach items="${requestScope.genresList}" var="genre">
                            <option value="${genre}">${genre}</option>
                        </c:forEach>
                    </select>
                </p>
            </form>
        </th>

        <% if (role.equals("1")) {%>
        <th id="edit">Edit</th>
        <th id="delete">Delete</th>
        <%}%>
    </tr>

    <c:forEach var="edition" items="${requestScope.editionList}">
        <tr>
            <td>${edition.title}</td>
            <td>
                <img src="${edition.imagePath}" alt="${edition.title}" width="150" height="200"
                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>
            </td>
            <td>
                <c:set var="key" value="${edition.genreId}"/>
                <c:out value="${requestScope.genreMap.get(key)}"/>
            </td>

            <% if (role.equals("1")) {%>
                <%--Edit button
                        Available only for admin--%>
            <td>
                <button onclick="document.getElementById('${edition.title}').style.display='block'">Edit</button>

                <div id="${edition.title}" class="modal">
                    <span onclick="document.getElementById('${edition.title}').style.display='none'" class="close"
                          title="Close Modal">×</span>
                    <form class="modal-content"
                          action="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list/update_edition" method="get">
                        <div class="container">
                            <h1>${edition.title}</h1>
                            <img src="${edition.imagePath}" alt="${edition.title}" width="150" height="200"
                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>

                            <p>Edit this edition?</p>

                            <div class="clearfix">
                                <button type="button"
                                        onclick="document.getElementById('${edition.title}').style.display='none'"
                                        class="cancel">Cancel
                                </button>
                                <button type="submit" name="edit_edition_title" value="${edition.title}" class="buy-btn">Edit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </td>

                <%--Delete button
                    Available only for admin--%>
            <td>
                <button onclick="document.getElementById('${edition.title}2').style.display='block'">Delete edition
                </button>

                <div id="${edition.title}2" class="modal">
                    <span onclick="document.getElementById('${edition.title}2').style.display='none'" class="close"
                          title="Close Modal">×</span>
                    <form class="modal-content"
                          action="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list/delete_edition" method="post">
                        <div class="container">
                            <h1>${edition.title}</h1>
                            <img src="${edition.imagePath}" alt="${edition.title}" width="150" height="200"
                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>

                            <p>Delete this edition?</p>

                            <div class="clearfix">
                                <button type="button"
                                        onclick="document.getElementById('${edition.title}2').style.display='none'"
                                        class="cancel">Cancel
                                </button>
                                <button type="submit" name="edition_title" value="${edition.title}" class="delete-btn">
                                    Delete
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </td>
            <%}%>
        </tr>
    </c:forEach>
</table>

<%--For displaying Previous link except 1st page --%>
<c:if test="${requestScope.currentPage != 1}">
    <td><a href="http://localhost:8080/edition_list?page=${requestScope.currentPage - 1}">Previous</a></td>
</c:if>

<%--For displaying Page numbers--%>
<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="http://localhost:8080/cabinet/admin_console/global_edition_list?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--For displaying Next link --%>
<c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
    <td><a href="http://localhost:8080/edition_list?page=${requestScope.currentPage + 1}">Next</a></td>
</c:if>

</body>
</html>

