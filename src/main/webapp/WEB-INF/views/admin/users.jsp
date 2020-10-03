<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>


<jsp:include page="/WEB-INF/views/parts/header.jsp"/>

<div>
    <h2><fmt:message key="text.admin.users.page"/></h2>
</div>

<c:forEach var="user" items="${requestScope.users}">
    <ul>
        <li>
            User ID: ${user.id} <br>
            User e-mail: ${user.email} <br>
            User status: ${user.role}
        </li>
    </ul>
    <hr/>
</c:forEach>


<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>
