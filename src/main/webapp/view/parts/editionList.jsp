<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : sessionScope.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization/locale" var="locale"/>

<%--<%--%>
<%--    String role = "";--%>
<%--    if (session.getAttribute("role") != null) {--%>
<%--        role = (String) session.getAttribute("role");--%>
<%--    }--%>
<%--%>--%>
<div class="container my-5 py-3">
    <div class="shadow p-3 mb-5 bg-body rounded">
        <table class="table table-hover align-middle mb-0 bg-white">
            <thead class="bg-light">
            <tr>
                <th class="col-2 text-center fs-5" scope="col" id="title_image">
                    <fmt:message key="edition_list.magazine" bundle="${locale}"/>
                </th>

                <th class="col-5 text-center fs-5" scope="col" id="title">
                    <fmt:message key="edition_list.title" bundle="${locale}"/>
                </th>

                <th class="col-2 text-center" scope="col" id="genres">
                    <form action="${pageContext.request.contextPath}/edition_list" method="get">
                        <select id="genreFilter" name="genreFilter" onchange="this.form.submit()">
                            <option value="0" ${requestScope.genreFilter == '0' ? 'selected' : ''} >*</option>
                            <c:forEach items="${requestScope.genresList}" var="genre">
                                <c:set var="genreId" value="${genre.id}"/>
                                <c:choose>
                                    <c:when test="${language == 'ua'}">
                                        <option value="${genreId}" ${sessionScope.genreFilter == genreId ? 'selected' : ''}>
                                                ${genre.genreUa}
                                        </option>
                                    </c:when>
                                    <c:when test="${language == 'en'}">
                                        <option value="${genreId}" ${sessionScope.genreFilter == genreId ? 'selected' : ''}>
                                                ${genre.genreEn}
                                        </option>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </form>
                </th>

                <th class="text-center fs-5" scope="col" id="price">
                    <fmt:message key="edition_list.price" bundle="${locale}"/>
                </th>

                <c:if test="${sessionScope.user != null}">
                    <th class=" col-2 text-center fs-5" scope="col" id="buy">
                        <fmt:message key="edition_list.buy" bundle="${locale}"/>
                    </th>
                </c:if>
            </tr>
            </thead>

            <tbody class="table-group-divider">
            <c:forEach var="edition" items="${requestScope.editionList}">
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <img src="${edition.imagePath}" alt="${edition.titleEn}" style="width: 160px"
                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/img/empty_title_placeholder/No_Image_Placeholder.jpg';"/>
                        </div>
                    </td>

                    <td>
                        <p class="fw-bold">
                            <c:choose>
                                <c:when test="${language == 'ua'}">
                                    <c:out value="${edition.titleUa}"/>
                                </c:when>
                                <c:when test="${language == 'en'}">
                                    <c:out value="${edition.titleEn}"/>
                                </c:when>
                            </c:choose>
                        </p>
                        <p class="text-muted mb-0 pe-5">
                            <c:choose>
                                <c:when test="${language == 'ua'}">
                                    <c:out value="${edition.textUa}"/>
                                </c:when>
                                <c:when test="${language == 'en'}">
                                    <c:out value="${edition.textEn}"/>
                                </c:when>
                            </c:choose>
                        </p>
                    </td>

                    <td class="text-center">
                        <span class="badge text-bg-success fs-6">
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
                        </span>
                    </td>

                    <td class="text-center">
                        <div class="fw-bold fs-5">
                                ${edition.price}
                        </div>
                    </td>

                    <c:if test="${sessionScope.user.role eq '3' or sessionScope.user.role eq '2'}">
                        <td class="text-center">
                            <button class="btn btn-primary btn-sm fw-bold"
                                    onclick="document.getElementById('${edition.id}').style.display='block'">
                                <fmt:message key="edition_list.buy" bundle="${locale}"/>
                            </button>

                            <div id="${edition.id}" class="modal">
                    <span onclick="document.getElementById('${edition.id}').style.display='none'" class="close"
                          title="Close Modal">×</span>
                                <form class="modal-content"
                                      action="${pageContext.request.contextPath}/edition_list/buy_edition" method="get">
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

                                        <p>
                                            <fmt:message key="edition_list.buy_this" bundle="${locale}"/>
                                        </p>

                                        <div class="clearfix">
                                            <button type="button"
                                                    onclick="document.getElementById('${edition.id}').style.display='none'"
                                                    class="cancel">
                                                <fmt:message key="edition_list.cancel" bundle="${locale}"/>
                                            </button>
                                            <button type="submit" name="buy_edition_id" value="${edition.id}"
                                                    class="buy-btn">
                                                <fmt:message key="edition_list.buy" bundle="${locale}"/>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </td>
                    </c:if>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

