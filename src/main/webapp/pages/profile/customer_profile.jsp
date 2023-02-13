<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale"/>
<c:set var="user" value="${sessionScope.current_user}"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>
<body class="vw-100 vh-100 mh-100 mw-100 p-3 bg-transparent">
<cstm:navBar/>
    <div class="w-100 d-flex gap-2 align-items-center">
        <p class="h3 navbar-text d-block">${user.name}</p>
        <p class="h3 navbar-text d-block">${user.surname}</p>
        <span class="badge bg-secondary text-dark">
            <fmt:message key="users.label.customer"/>
        </span>
    </div>
    <div class="w-100">
        <p class="h3"><fmt:message key="users.label.my_tours"/></p>
    </div>
    <c:if test="${fn:length(requestScope.orders)<1}">
        <div class="alert alert-warning " role="alert">
            <fmt:message key="users.label.no_tours"/>
        </div>
    </c:if>
    <c:if test="${fn:length(requestScope.orders)>0}">
        <div class="d-flex gap-2 w-100">
            <c:forEach items="${requestScope.orders}" var="order">
                <div class="card bg-white border-0 rounded-0 shadow-sm" style="max-width: 280px;">
                    <div class="card-im d-block w-100 position-relative shadow-sm">
                        <c:url value="../../img/tour-img/${order.tourImage}" var="imagePath"/>
                        <img src="${imagePath}" class="img-fluid" alt="..."/>
                        <div class="position-absolute" style="top: 8px; right: 16px;">
                            <div class="bg-light text-black p-2 rounded-0">
                                <c:if test="${order.tourType=='vacation'}">
                                    <fmt:message key="tour.type-vacation"/>
                                </c:if>
                                <c:if test="${order.tourType=='excursion'}">
                                    <fmt:message key="tour.type-excursion"/>
                                </c:if>
                                <c:if test="${order.tourType=='shopping'}">
                                    <fmt:message key="tour.type-shopping"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <a href="/tour-agency/catalog/tour?id=${order.tourId}" class="card-title text-start h5 fw-bold btn btn-link text-decoration-none link-dark p-0" style="text-transform: uppercase">
                                ${order.tourTitle}
                        </a>
                        <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                            <div class="row">
                                <div class="row">
                                    <div class="col">
                                            <span class="card-text fw-bold">
                                                <fmt:message key="users.profile.order.label.ticket_count"/>
                                            </span>
                                        <span class="card-text">
                                                ${order.ticketCount}
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                            <div class="row">
                                <div class="row">
                                    <div class="col-auto">
                                        <c:choose>
                                            <c:when test="${order.status=='registered'}">
                                                <span>
                                                <fmt:message key="users.profile.order.payment_amount"/>&nbsp;
                                            </span>
                                                <span>
                                                        ${order.totalPrice}
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="users.profile.orderBean.label.status.${order.status}"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</body>
</html>
