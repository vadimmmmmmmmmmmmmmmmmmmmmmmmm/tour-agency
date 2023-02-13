<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Error occurred!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>
<body class="vw-100 vh-100 bg-transparent">
<section class="w-100 h-100 d-flex justify-content-center align-items-center">
    <div>
        <c:forEach items="${sessionScope.messages}" var="message">
            <div class="alert shadow-sm rounded-0 border-0
                <c:choose>
                    <c:when test="${message.type=='INFO'}">
                        alert-info
                    </c:when>
                    <c:when test="${message.type=='ERROR'}">
                        alert-danger
                    </c:when>
                </c:choose>">
                <fmt:message key="${message.messageCode}"/>
            </div>
        </c:forEach>
    </div>
</section>
</body>
</html>