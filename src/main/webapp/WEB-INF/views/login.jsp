<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>


<jsp:include page="/WEB-INF/views/parts/header.jsp"/>

<div class="page-wrapper">
    <div class="container">

        <h1 class="flow-text"><fmt:message key="text.enter.login.and.password"/></h1>

        <form method="post" action="${pageContext.request.contextPath}/exhibitions/home">

            <input type="text" pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$" required
                   placeholder="<fmt:message key="text.email"/>" name="email"> <br>

            <input type="password" required placeholder="<fmt:message key="text.password"/>" name="password"> <br>

<%--            <input type="hidden" name="command" value="loginIntoSystem">--%>

            <input class="btn" type="submit" value="<fmt:message key="text.enter"/>">

        </form>

        <c:if test="${sessionScope.incorrectInput != null}">
            <fmt:message key="text.incorrect.password"/>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>
