<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<jsp:include page="/WEB-INF/views/parts/header.jsp"/>
<div class="container">
    <div class="row">
        <h3 class="flow-text"><fmt:message key="text.congratulations"/></h3>
    </div>
    <div class="row">
        <%-- list of exhibitions --%>
        <div class="col s8">
            <div class="list-of-shows">
                <c:forEach var="exhibition" items="${requestScope.exhibitions}">
                    <div class="show-item">
                        <div class="row">
                            <div class="col s12">
                                <strong><fmt:message key="text.exhibition.topic"/>:</strong>
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
                            <div class="col s9">
                                <fmt:message key="text.exhibition.status"/>:
                                <span>${exhibition.status}</span>
                            </div>
                            <div class="col s3">
                                <form method="post"
                                      action="${pageContext.request.contextPath}/exhibitions/user/buy-ticket">
                                    <label>
                                        <input type="number" hidden name="id" value="${exhibition.id}"/>
                                    </label>
                                    <input type="submit" class="btn"
                                           value="<fmt:message key="text.exhibition.buy"/>"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <%--pagination--%>
            <ul class="pagination">
                <c:if test="${requestScope.currentPage ne 1}">
                    <li class="disabled"><a
                            href="${pageContext.request.contextPath}/exhibitions/main-page?page=1"><i
                            class="material-icons">chevron_left</i></a></li>
                </c:if>

                <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.currentPage eq i}">
                            <li class="disabled"><a
                                    href="${pageContext.request.contextPath}/exhibitions/main-page?page=${i}">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="waves-effect"><a
                                    href="${pageContext.request.contextPath}/exhibitions/main-page?page=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                    <li class="waves-effect">
                        <a href="${pageContext.request.contextPath}/exhibitions/main-page?page=${requestScope.currentPage + 1}">
                            <i class="material-icons">chevron_right</i>
                        </a>
                    </li>
                </c:if>

            </ul>
        </div>
        <%--login and register--%>
        <div class="col s4">
            <div class="login-register-window">
                <c:choose>
                    <c:when test="${empty sessionScope.role}">
                        <div>
                            <form method="post" action="${pageContext.request.contextPath}/exhibitions/login">
                                <input class="btn" type="submit" value="<fmt:message key="text.enter"/>">
                            </form>
                        </div>
                        <div>
                            <form method="post" action="${pageContext.request.contextPath}/exhibitions/registration">
                                <input class="btn" type="submit" value="<fmt:message key="text.registration"/>">
                            </form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <form method="post" action="${pageContext.request.contextPath}/exhibitions/home">
                                <input class="btn" type="submit" value="<fmt:message key="text.home"/>">
                            </form>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>