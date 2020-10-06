<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<jsp:include page="/WEB-INF/views/parts/header.jsp"/>
<div class="container">
    <div class="row" align="center">
        <h3 class="flow-text"><fmt:message key="text.congratulations"/></h3>
    </div>
    <div class="row">
        <%--           list of exhibitions --%>
        <div class="col s8">

            <div class="list-of-shows">
                <c:forEach var="exhibition" items="${requestScope.exhibitions}">
                    <div class="show-item">
                        <div class="row">
                            <div class="col s1">
                                <b>ID: ${exhibition.id}</b>
                            </div>
                            <div class="col s11">
                                <b><fmt:message key="text.exhibition.topic"/>:</b>
                                <div class="show-item-title">
                                        ${exhibition.topic}
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12">
                                <fmt:message key="text.exhibition.category"/>:
                                <span>${exhibition.category.name}</span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s6">
                                <fmt:message key="text.exhibition.dates"/>:
                                <span>${exhibition.startDate} - ${exhibition.endDate}</span>
                            </div>
                            <div class="col s6">
                                <fmt:message key="text.exhibition.time"/>:
                                <span>${exhibition.startTime} - ${exhibition.endTime}</span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s3">
                                <fmt:message key="text.exhibition.price"/>:
                                <span>${exhibition.price}</span>
                            </div>
                            <div class="col s3">
                                <fmt:message key="text.exhibition.places"/>:
                                <span>${exhibition.capacity}</span>
                            </div>
                            <div class="col s3">
                                <fmt:message key="text.exhibition.placesLeft"/>:
                                <span>${exhibition.remainingSeats}</span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s2">
                                <fmt:message key="text.exhibition.hall-name"/>:
                            </div>
                            <c:forEach var="hall" items="${exhibition.hallList}">
                                <div class="col s2">
                                    <span>${hall.name}</span>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="row">
                            <div class="col s3">
                                <fmt:message key="text.exhibition.status"/>:
                                <span>${exhibition.status}</span>
                            </div>
                            <div class="col s3">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/exhibitions/admin/delete-exhibition">
                                    <input type="number" hidden name="id" value="${exhibition.id}"/>
                                    <input type="submit" class="btn"
                                           value="<fmt:message key="text.admin.delete.exhibition"/>"/>
                                </form>
                            </div>
                            <div class="col s3">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/exhibitions/admin/deactivate-exhibition">
                                    <input type="number" hidden name="id" value="${exhibition.id}"/>
                                    <input type="submit" class="btn"
                                           value="<fmt:message key="text.admin.deactivate.exhibition"/>"/>
                                </form>
                            </div>
                            <div class="col s3">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/exhibitions/admin/activate-exhibition">
                                    <input type="number" hidden name="id" value="${exhibition.id}"/>
                                    <input type="submit" class="btn"
                                           value="<fmt:message key="text.admin.activate.exhibition"/>"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="col s4">
            <div class="login-register-window">
                <div>
                    <form method="post" action="${pageContext.request.contextPath}/exhibitions/login">
                        <input class="btn" type="submit" value="<fmt:message key="text.enter"/>">
                    </form>
                </div>
                <div>
                    <form method="post" action="${pageContext.request.contextPath}/exhibitions/registration">
                        <input class="btn" type="submit" value= <fmt:message key="text.registration"/>>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>