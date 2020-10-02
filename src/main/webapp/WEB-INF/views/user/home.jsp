<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<html>
<head>
    <title><fmt:message key="text.user.homepage.title"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/views/parts/header.jsp"/>

<div>
    <h3><fmt:message key="text.user.homepage.title"/></h3>
</div>

<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>
</body>
</html>
