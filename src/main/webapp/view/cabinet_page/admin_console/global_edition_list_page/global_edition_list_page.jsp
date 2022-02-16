<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session" />
<%-- Deprecated --%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="localization/locale" />
<%-- A custom property loader was used to work with Cyrillic (UTF-8 format) --%>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Main edition list</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_css_modules/background.css"
          type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edition_page/edition_list_page_style.css"
          type="text/css">
</head>
<body>
<jsp:include page="/view/parts/header.jsp"/><br/>

<%
    String role = "";
    if (session.getAttribute("role") != null) {
        role = (String) session.getAttribute("role");
    }
%>

<% if (role.equals("1")) {%>
<a href="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list/add_new_edition">${requestScope.localization.getString("edition_list.link.add_edition")}</a><br/>
<%}%>


<table border="1" cellpadding="5" cellspacing="3">
    <tr>
        <th id="title">${requestScope.localization.getString("edition_list.text.title")}</th>
        <th id="title_image">${requestScope.localization.getString("edition_list.text.image")}</th>
        <th id="genres">
            <form action="${pageContext.request.contextPath}/edition_list" method="get">
                <p><label for="genre">${requestScope.localization.getString("edition_list.text.genre")}</label><br/>
                    <select id="genre" name="genre" onchange="this.form.submit()">
                        <c:forEach items="${requestScope.genresList}" var="genre">
                            <option value="${genre}">${genre}</option>
                        </c:forEach>
                    </select>
                </p>
            </form>
        </th>

        <% if (role.equals("1")) {%>
        <th id="edit">${requestScope.localization.getString("edition_list.button.edit")}</th>
        <th id="delete">${requestScope.localization.getString("edition_list.button.delete")}</th>
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
                <button class="green-btn" onclick="document.getElementById('${edition.id}').style.display='block'">${requestScope.localization.getString("edition_list.button.edit")}</button>

                <div id="${edition.id}" class="modal">
                    <span onclick="document.getElementById('${edition.id}').style.display='none'" class="close"
                          title="Close Modal">×</span>
                    <form class="modal-content"
                          action="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list/update_edition" method="get">
                        <div class="container">
                            <h1>${edition.title}</h1>
                            <img src="${edition.imagePath}" alt="${edition.id}" width="150" height="200"
                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>

                            <p>${requestScope.localization.getString("edition_list.text.edit_text")}</p>

                            <div class="clearfix">
                                <button type="button"
                                        onclick="document.getElementById('${edition.id}').style.display='none'"
                                        class="cancel">${requestScope.localization.getString("edition_list.button.cancel")}
                                </button>
                                <button type="submit" name="edit_edition_id" value="${edition.id}" class="buy-btn">${requestScope.localization.getString("edition_list.button.edit")}</button>
                            </div>
                        </div>
                    </form>
                </div>
            </td>

                <%--Delete button
                    Available only for admin--%>
            <td>
                <button class="red-btn" onclick="document.getElementById('${edition.id}2').style.display='block'">${requestScope.localization.getString("edition_list.button.delete")}
                </button>

                <div id="${edition.id}2" class="modal">
                    <span onclick="document.getElementById('${edition.id}2').style.display='none'" class="close"
                          title="Close Modal">×</span>
                    <form class="modal-content"
                          action="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list/delete_edition" method="post">
                        <div class="container">
                            <h1>${edition.title}</h1>
                            <img src="${edition.imagePath}" alt="${edition.id}" width="150" height="200"
                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>

                            <p>${requestScope.localization.getString("edition_list.text.delete_text")}</p>

                            <div class="clearfix">
                                <button type="button"
                                        onclick="document.getElementById('${edition.id}2').style.display='none'"
                                        class="cancel">${requestScope.localization.getString("edition_list.button.cancel")}
                                </button>
                                <button type="submit" name="edition_id" value="${edition.id}" class="delete-btn">
                                        ${requestScope.localization.getString("edition_list.button.delete")}
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
    <td><a href="http://localhost:8080/edition_list?page=${requestScope.currentPage - 1}">${requestScope.localization.getString("edition_list.button.previous")}</a></td>
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
    <td><a href="http://localhost:8080/edition_list?page=${requestScope.currentPage + 1}">${requestScope.localization.getString("edition_list.button.next")}</a></td>
</c:if>

</body>
</html>

