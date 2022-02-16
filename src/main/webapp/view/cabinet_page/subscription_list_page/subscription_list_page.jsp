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

<h1>${requestScope.localization.getString("subscriptions.text.hello")} ${sessionScope.login} !</h1>
<h2>${requestScope.localization.getString("subscriptions.text.subscriptions_list")}</h2>

<table border="1" cellpadding="5" cellspacing="3">
    <tr>
        <th id="title">${requestScope.localization.getString("subscriptions.text.title")}</th>
        <th id="title_image">${requestScope.localization.getString("subscriptions.text.image")}</th>
        <th id="genres">
            <form action="${pageContext.request.contextPath}/cabinet/subscription_list" method="get">
                <p><label for="genre">${requestScope.localization.getString("subscriptions.text.genre")}</label><br/>
                    <select id="genre" name="genre" onchange="this.form.submit()">
                        <c:forEach items="${requestScope.genresList}" var="genre">
                            <c:choose>
                                <c:when test="${language == 'ua'}">
                                    <option value="${genre.genreUa}">${genre.genreUa}</option>
                                </c:when>
                                <c:when test="${language == 'en'}">
                                    <option value="${genre.genreEn}">${genre.genreEn}</option>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </select>
                </p>
            </form>
        </th>
        <th id="unsubscribe">Unsubscribe</th>
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
            </td>

            <%-- Unsubscribe button --%>
            <td>
                <button class="red-btn" onclick="document.getElementById('${edition.id}').style.display='block'">${requestScope.localization.getString("subscriptions.text.unsubscribe")}
                </button>

                <div id="${edition.id}" class="modal">
                    <span onclick="document.getElementById('${edition.id}').style.display='none'" class="close"
                          title="Close Modal">×</span>
                    <form class="modal-content"
                          action="${pageContext.request.contextPath}/cabinet/subscription_list/unsubscribe" method="post">
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

                            <p>${requestScope.localization.getString("subscriptions.text.unsubscribe_text")}</p>

                            <div class="clearfix">
                                <button type="button"
                                        onclick="document.getElementById('${edition.id}').style.display='none'"
                                        class="cancel">${requestScope.localization.getString("subscriptions.button.cancel")}</button>
                                <button type="submit" name="edition_id" value="${edition.id}"
                                        class="delete-btn">${requestScope.localization.getString("subscriptions.text.unsubscribe")}</button>
                            </div>
                        </div>
                    </form>
                </div>
            </td>
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
                    <td><a href="http://localhost:8080/cabinet/subscription_list?page=${i}">${i}</a></td>
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
