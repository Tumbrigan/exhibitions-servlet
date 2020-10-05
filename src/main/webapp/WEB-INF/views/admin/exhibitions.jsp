<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<jsp:include page="/WEB-INF/views/parts/header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col s8">
            <h3><fmt:message key="text.admin.exhibitions.page"/></h3>
        </div>
    </div>

    <div class="row">
        <!-- list-of-shows -->
        <div class="col s8">
            <div class="list-of-shows">

                <c:forEach var="exhibition" items="${requestScope.exhibitions}">

                    <div class="show-item">
                        <div class="row">
                            <div class="col">
                                <b>ID: ${exhibition.id}</b>
                            </div>
                            <div class="col">
                                <div class="show-item-title">
                                    Topic: ${exhibition.topic}
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12">
                                Category: ${exhibition.category.name}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s4">
                                Dates: ${exhibition.startDate} - ${exhibition.endDate}
                            </div>
                            <div class="col s4">
                                Time: ${exhibition.startTime} - ${exhibition.endTime}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s3">
                                Price: ${exhibition.price}
                            </div>
                            <div class="col s3">
                                Places left: ${exhibition.remainingSeats}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s2">Hall name:</div>
                            <c:forEach var="hall" items="${exhibition.hallList}">
                                <div class="col s2">
                                    <span>${hall.name}</span>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="row">
                            <div class="col s3">
                                Status: ${exhibition.status}<br>
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
        <!-- show-create-form -->
        <div class="col s4">
            <div class="show-create-form">
                <form action="${pageContext.request.contextPath}/exhibitions/admin/create-exhibition" method="post">





                    <label for="category"><fmt:message key="text.admin.select.category"/></label>
                    <select class="browser-default" id="category" name="categoryID">
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


                    <input type="text" name="datetest" class="datepicker">



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
                    <input class="browser-default" type="time" id="end_time" name="time-end"
                           value="12:00">
                    <br>
                    <label for="price"><fmt:message key="text.admin.insert.price"/></label>
                    <input id="price" type="number" min="0" max="1500" step="25" required
                           placeholder="0-1500" name="price">
                    <br>
                    <label for="places"><fmt:message key="text.admin.insert.places"/></label>
                    <input id="places" type="number" min="0" max="${requestScope.maxSeatAmount}" required
                           placeholder="0-${requestScope.maxSeatAmount}" name="places">
                    <c:forEach var="hall" items="${requestScope.halls}">
                        <label>
                            <input type="checkbox" name="hall_id" value="${hall.id}"/>
                            <span>${hall.name} (${hall.capacity})</span><br>
                        </label>
                    </c:forEach>
                    <input type="submit" class="btn" value="<fmt:message key="text.admin.add.exhibition"/>">
                </form>

                <c:forEach items="${sessionScope.occupiedHallsByExhId}" var="entry">
                    The ${entry.key} hall is occupied by exhibition with ${entry.value} id<br>
                </c:forEach>
                ${requestScope.busyHalls}
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/parts/footer.jsp"/>