<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="text"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

    <style>
        nav {
            background-color: #00796b;
        }

        .page-footer.my-footer {
            padding-top: 0;
            background-color: #00796b;
        }

        .page-footer.my-footer .row {
            margin-bottom: 0;
        }

        body {
            display: grid;
            grid-template-rows: 1fr auto;
            height: 100vh;
            padding-top: 64px;
        }

        nav {
            position: fixed;
            top: 0;
            left: 0;
        }


        .main {
            display: block;
            border: 1px solid red;
            margin: 20px 0;
        }

        body {
            padding-top: 64px;
        }

        nav {
            position: fixed;
            top: 0px;
            left: 0;
        }

        .container {
        }

        .list-of-shows {
            border: 1px solid red;
            margin-bottom: 30px;
        }

        .show-item {
            border: 3px solid #00796b; /* Белая рамка */
            border-radius: 20px; /* Радиус скругления */
        }

        .show-item .row {
            border: 1px solid red;
        }

        .show-item .col {
            border: 1px solid red;
        }

        .show-create-form {
            border: 3px solid #00796b; /* Белая рамка */
            border-radius: 20px; /* Радиус скругления */
        }

        .row {
            padding: 10px;
        }


    </style>

    <title><fmt:message key="text.title"/></title>
</head>
<body>
    <nav>
        <div class="nav-wrapper">
            <div class="container">
                <a class="brand-logo"><fmt:message key="text.title"/></a>
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li><a href="${pageContext.request.contextPath}/"><fmt:message key="text.main"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/exhibitions/logout"><fmt:message key="text.logout"/></a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/exhibitions/language/en">English</a></li>
                    <li><a href="${pageContext.request.contextPath}/exhibitions/language/ua">Українська</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <%-- start main--%>
    <main class="main-content" >