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
        <li class="page-item disabled">
            <a class="page-link">Previous</a>
        </li>
        <li class="page-item"><a class="page-link" href="#">1</a></li>
        <li class="page-item"><a class="page-link" href="#">2</a></li>
        <li class="page-item"><a class="page-link" href="#">3</a></li>
        <li class="page-item"><a class="page-link" href="#">4</a></li>
        <li class="page-item"><a class="page-link" href="#">5</a></li>
        <li class="page-item"><a class="page-link" href="#">6</a></li>
        <li class="page-item"><a class="page-link" href="#">7</a></li>
        <li class="page-item">
            <a class="page-link" href="#">Next</a>
        </li>
    </ul>
</nav>

<%--For displaying Previous link except 1st page --%>
<%--<c:if test="${requestScope.currentPage != 1}">--%>
<%--    <td>--%>
<%--        <a href="http://localhost:8080/edition_list?page=${requestScope.currentPage - 1}">--%>
<%--            <fmt:message key="edition_list.previous" bundle="${locale}"/>--%>
<%--        </a>--%>
<%--    </td>--%>
<%--</c:if>--%>

<%--&lt;%&ndash;For displaying Page numbers&ndash;%&gt;--%>
<%--<table border="1" cellpadding="5" cellspacing="5">--%>
<%--    <tr>--%>
<%--        <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">--%>
<%--            <c:choose>--%>
<%--                <c:when test="${requestScope.currentPage eq i}">--%>
<%--                    <td>${i}</td>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <td><a href="http://localhost:8080/edition_list?page=${i}">${i}</a></td>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
<%--        </c:forEach>--%>
<%--    </tr>--%>
<%--</table>--%>

<%--&lt;%&ndash;For displaying Next link &ndash;%&gt;--%>
<%--<c:if test="${requestScope.currentPage lt requestScope.noOfPages}">--%>
<%--    <td>--%>
<%--        <a href="http://localhost:8080/edition_list?page=${requestScope.currentPage + 1}">--%>
<%--            <fmt:message key="edition_list.next" bundle="${locale}"/>--%>
<%--        </a>--%>
<%--    </td>--%>
<%--</c:if>--%>
