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
        <th id="title">
            <form action="${pageContext.request.contextPath}/edition_list" method="get">
                <input id="titleFilter" name="sortFilter" hidden value="${language == 'ua' ? 'titleUa' : 'titleEn'}">
                <button onclick="this.form.submit()">${requestScope.localization.getString("edition_list.text.title")}</button>
            </form>
        </th>
        <th id="title_image">${requestScope.localization.getString("edition_list.text.image")}</th>
        <th id="genres">
            <form action="${pageContext.request.contextPath}/cabinet/admin_console/global_edition_list" method="get">
                <p><label for="genreFilter">${requestScope.localization.getString("edition_list.text.genre")}</label><br/>
                    <select id="genreFilter" name="genreFilter" onchange="this.form.submit()">
                        <option value="0" ${sessionScope.genreFilter == '0' ? 'selected' : ''} >*</option>
                        <c:forEach items="${requestScope.genresList}" var="genre">
                            <c:set var="genreId" value="${genre.id}"/>
                            <c:choose>
                                <c:when test="${language == 'ua'}">
                                    <option value="${genreId}" ${sessionScope.genreFilter == genreId ? 'selected' : ''}>${genre.genreUa}</option>
                                </c:when>
                                <c:when test="${language == 'en'}">
                                    <option value="${genreId}" ${sessionScope.genreFilter == genreId ? 'selected' : ''}>${genre.genreEn}</option>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </select>
                </p>
            </form>
        </th>
        <th id="price">
            <form action="${pageContext.request.contextPath}/edition_list" method="get">
                <input id="priceFilter" name="sortFilter" hidden value="price">
                <button onclick="this.form.submit()">${requestScope.localization.getString("edition_list.text.price")}</button>
            </form>
        </th>

        <% if (role.equals("1")) {%>
        <th id="edit">${requestScope.localization.getString("edition_list.button.edit")}</th>
        <th id="delete">${requestScope.localization.getString("edition_list.button.delete")}</th>
        <%}%>
    </tr>

    <c:forEach var="edition" items="${requestScope.editionList}">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${language == 'ua'}">
                        <c:out value="${edition.titleUa}"/>
                    </c:when>
                    <c:when test="${language == 'en'}">
                        <c:out value="${edition.titleEn}"/>
                    </c:when>
                </c:choose>
            </td>
            <td>
                <img src="${edition.imagePath}" alt="${edition.titleEn}" width="150" height="200"
                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>
            </td>
            <td>
                <c:set var="key" value="${edition.genreId}"/>
                <c:forEach var="genre" items="${requestScope.genresList}">
                    <c:choose>
                        <c:when test="${genre.id == key}">
                            <c:choose>
                                <c:when test="${language == 'ua'}">
                                    <c:out value="${genre.genreUa}"/>
                                </c:when>
                                <c:when test="${language == 'en'}">
                                    <c:out value="${genre.genreEn}"/>
                                </c:when>
                            </c:choose>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <c:out value="${requestScope.genreMap.get(key)}"/>
            </td>
            <td>${edition.price}</td>


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
                            <h1>
                                <c:choose>
                                    <c:when test="${language == 'ua'}">
                                        <c:out value="${edition.titleUa}"/>
                                    </c:when>
                                    <c:when test="${language == 'en'}">
                                        <c:out value="${edition.titleEn}"/>
                                    </c:when>
                                </c:choose>
                            </h1>
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
                            <h1>
                                <c:choose>
                                    <c:when test="${language == 'ua'}">
                                        <c:out value="${edition.titleUa}"/>
                                    </c:when>
                                    <c:when test="${language == 'en'}">
                                        <c:out value="${edition.titleEn}"/>
                                    </c:when>
                                </c:choose>
                            </h1>
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

