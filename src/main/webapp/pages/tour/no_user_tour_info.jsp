<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale"/>
<c:set var="tour" value="${requestScope.tour}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${tour.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>

<body class="vw-100 mw-100 p-3 bg-transparent">
    <cstm:navBar/>
    <section id="tourInfo" class="position-relative d-flex flex-wrap gap-2 justify-content-start align-items-stretch w-100">
        <div class="w-100 border-0 rounded-0 position-relative shadow-sm">
            <div class="ratio" style="--bs-aspect-ratio: 25%;">
                <c:url value="/img/tour-img/${tour.imageFile}" var="imagePath"/>
                <img class="w-100 h-100" style="object-fit: cover;" src="${imagePath}" alt="..."/>
            </div>
            <div class="position-absolute" style="top: 8px; right: 16px;">
                <div class="bg-light text-black p-2 rounded-0">
                    <c:if test="${tour.type=='vacation'}">
                        <fmt:message key="tour.type-vacation"/>
                    </c:if>
                    <c:if test="${tour.type=='excursion'}">
                        <fmt:message key="tour.type-excursion"/>
                    </c:if>
                    <c:if test="${tour.type=='shopping'}">
                        <fmt:message key="tour.type-shopping"/>
                    </c:if>
                </div>
                <c:if test="${tour.onFire==true}">
                    <div class="text-center bg-danger text-black p-2 rounded-0 shadow-sm">
                        <i class="fa-solid fa-fire"></i>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="card w-auto bg-white border-0 rounded-0 shadow-sm mb-2 flex-grow-1">
            <div class="card-body">
                <p class="card-title h1 fw-bold">
                    ${tour.title}
                </p>
                <p class="card-text">
                    ${tour.description}
                </p>
                <c:if test="${tour.hotelName!='-'}">
                    <hr>
                    <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                        <div class="row">
                            <div class="col text-center">
                                <i class="fa-solid fa-hotel"></i> ${tour.hotelName}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col text-center">
                                <c:if test="${tour.hotelRating>=3}"><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i><i class="bi bi-star-fill"></i></c:if><c:if test="${tour.hotelRating>=4}"><i class="bi bi-star-fill"></i></c:if><c:if test="${tour.hotelRating>=5}"><i class="bi bi-star-fill"></i></c:if>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="card w-auto bg-white border-0 rounded-0 shadow-sm mb-2">
            <div class="card-body">
                <div class="w-auto container-fluid">
                    <div class="row">
                        <div class="col">
                            <p class="h3">
                                <fmt:message key="catalog.tour.label.schedule"/>
                            </p>
                        </div>
                    </div>
                    <div class="row bg-light">
                        <div class="col">
                            <fmt:message key="catalog.tour.label.departure_ticket_date"/>
                        </div>
                        <div class="col">
                            <fmt:message key="catalog.tour.label.departure_ticket_time"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            ${tour.departureTakeoffDate}
                        </div>
                        <div class="col">
                            ${tour.departureTakeoffTime}
                        </div>
                    </div>
                    <div class="row bg-light">
                        <div class="col">
                            <fmt:message key="catalog.tour.label.return_ticket_date"/>
                        </div>
                        <div class="col">
                            <fmt:message key="catalog.tour.label.return_ticket_time"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            ${tour.returnTakeoffDate}
                        </div>
                        <div class="col">
                            ${tour.returnTakeoffTime}
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="card w-auto bg-white border-0 rounded-0 shadow-sm flex-sm-grow-1">
            <div class="card-body">
                <p class="card-title">
                    <fmt:message key="catalog.tour.available_tickets"/>
                </p>
                <p class="card-text">
                    ${tour.ticketCount-tour.registeredTicketsCount-tour.paidTicketsCount}
                </p>
                <hr>
                <a href="/tour-agency/sign_in" class="btn btn-primary rounded-0 border-0 mt-3">
                    <fmt:message key="catalog.tour.label.sign_in_to_book"/>
                </a>
            </div>
        </div>
    </section>
</body>
</html>
