<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<html>
<head>
    <title><fmt:message key="text.admin.exhibitions.page"/></title>
</head>
<body>
<jsp:include page="/WEB-INF/views/parts/header.jsp"/>

<form action="${pageContext.request.contextPath}/exhibitions/create-category">
    <label for="category"><fmt:message key="text.admin.select.category"/></label>
        <select class="browser-default" id="category">
            <c:forEach var="category" items="${requestScope.categories}">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>
    <br>
    <label for="topic"><fmt:message key="text.admin.insert.topic"/></label>
    <input class="browser-default" type="text" id="topic" name="topic"
           required
           maxlength="255">
    <br>
    <label for="start"><fmt:message key="text.admin.select.startDate"/></label>
    <input class="browser-default" type="date" id="start" name="exh-start"
           value="2020-09-29"
           min="2020-09-29" max="2030-09-30">
    <br>
    <label for="end"><fmt:message key="text.admin.select.endDate"/></label>
    <input class="browser-default" type="date" id="end" name="exh-end"
           value="2020-09-29"
           min="2020-09-29" max="2030-09-30">
    <br>
    <label for="start_time"><fmt:message key="text.admin.select.startTime"/></label>
    <input class="browser-default" type="time" id="start_time" name="time-start"
           value="12:00">
    <br>
    <label for="end_time"><fmt:message key="text.admin.select.endTime"/></label>
    <input class="browser-default" type="time" id="end_time" name="time-start"
           value="12:00">
    <br>
    <label for="price"><fmt:message key="text.admin.insert.price"/></label>
    <input id="price" type="number" min="0" max="1500" step="25" required
           placeholder="0-1500" name="price">
    <br>
    <label for="places"><fmt:message key="text.admin.insert.places"/></label>
    <input id="places" type="number" min="0" max="${requestScope.maxSeatAmount}" required
           placeholder="0-${requestScope.maxSeatAmount}" name="places">
    <input type="submit" value="<fmt:message key="text.admin.add.exhibition"/>">
</form>

<div>
    <h3><fmt:message key="text.admin.exhibitions.page"/></h3>
</div>

<c:forEach var="exhibition" items="${requestScope.exhibitions}">
    <ul>
        <li>
            Topic: ${exhibition.topic}<br>
            Category: ${exhibition.category.name}<br>
            Dates: ${exhibition.startDate} - ${exhibition.startDate}<br>
            Time: ${exhibition.startTime} - ${exhibition.endTime}<br>
            Price: ${exhibition.price}<br>
            Places left: ${exhibition.remainingSeats}<br>
            <c:forEach var="hall" items="${exhibition.hallList}">
            <ul>
                <li>
                    Hall name: ${hall.name} <br>
                </li>
            </ul>
            </c:forEach>
            Status: ${exhibition.status}<br>
        </li>
    </ul>
    <hr/>
</c:forEach>

<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>
</body>
</html>