<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<jsp:include page="/WEB-INF/views/parts/header.jsp"/>
<div class="page-wrapper">
    <div class="container">
        <div class="row">
            <h3 class="flow-text"><fmt:message key="text.user.homepage.title"/></h3>
        </div>

        <c:if test="${(requestScope.ticketsList eq null) or
        (requestScope.ticketsList.size eq 0)}">
            <blockquote>
                <fmt:message key="text.user.homepage.no-one-ticket"/>
            </blockquote>
            <blockquote>
                <fmt:message key="text.user.homepage.where-get-ticket"/>
            </blockquote>
        </c:if>
    </div>
</div>
<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>