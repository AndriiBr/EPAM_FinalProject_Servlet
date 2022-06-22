<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization/locale" var="locale"/>

<nav aria-label="Page navigation example">
    <ul class="pagination justify-content-center mb-4 pb-4">
        <c:choose>
            <c:when test="${requestScope.currentPage eq '1'}">
                <li class="page-item disabled">
                    <button class="page-link" onclick="executeUrlParameter('currentPage', ${requestScope.currentPage - 1})">Previous</button>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <button class="page-link" onclick="executeUrlParameter('currentPage', ${requestScope.currentPage - 1})">Previous</button>
                </li>
            </c:otherwise>
        </c:choose>

        <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <li class="page-item active">
                        <button class="page-link" onclick="executeUrlParameter('currentPage', ${i})">${i}</button>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <button class="page-link" onclick="executeUrlParameter('currentPage', ${i})">${i}</button>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${requestScope.currentPage ne requestScope.numberOfPages}">
                <li class="page-item">
                    <button class="page-link" onclick="executeUrlParameter('currentPage', ${requestScope.currentPage + 1})">Next</button>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item  disabled">
                    <button class="page-link" onclick="executeUrlParameter('currentPage', ${requestScope.currentPage + 1})">Next</button>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>

<script src="${pageContext.request.contextPath}/js/addUrlParameter.js"></script>

<%--&lt;%&ndash;For displaying Next link &ndash;%&gt;--%>
<%--<c:if test="${requestScope.currentPage lt requestScope.noOfPages}">--%>
<%--    <td>--%>
<%--        <a href="http://localhost:8080/edition_list?page=${requestScope.currentPage + 1}">--%>
<%--            <fmt:message key="edition_list.next" bundle="${locale}"/>--%>
<%--        </a>--%>
<%--    </td>--%>
<%--</c:if>--%>
