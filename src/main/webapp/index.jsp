<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<jsp:include page="/WEB-INF/views/parts/header.jsp"/>

<style>
    .wrapper {
        display: flex;
        align-items: center;
        min-height: 75vh;
    }
</style>

<div class="wrapper">
    <div class="container">
        <h3 class="flow-text"><fmt:message key="text.congratulations"/></h3>
        <form method="post" action="${pageContext.request.contextPath}/exhibitions/login">
            <input class="btn" type="submit" value="<fmt:message key="text.enter"/>">
        </form>
        <form method="post" action="${pageContext.request.contextPath}/exhibitions/registration">
            <input class="btn" type="submit" value= <fmt:message key="text.registration"/>>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>