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
    <title>Reserve</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>
<body class="vw-100 vh-100 mh-100 mw-100 p-3 bg-transparent">
<div class="w-100 d-flex justify-content-center">
        <div class="card bg-white border-0 rounded-0 shadow-sm" style="min-width:460px; max-width:460px;">
            <div class="card-im w-100 position-relative shadow-sm">
                <div class="ratio" style="--bs-aspect-ratio: 25%;">
                    <c:url value="../../img/tour-img/${tour.imageFile}" var="imagePath"/>
                    <img src="${imagePath}" class="w-100 h-100" style="object-fit: cover;" alt="..."/>
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
            <div class="card-body">
                <p class="card-title h1 fw-bold p-0" style="text-transform: uppercase">
                    ${tour.title}
                </p>
                <p class="card-text">${tour.description}</p>
                <c:if test="${tour.hotelName!='-'}">
                    <hr>
                    <p class="h3">
                        <fmt:message key="catalog.tour.label.hotel"/>
                    </p>
                    <div class="ms-0">
                        <div class="row bg-light">
                            <div class="col">
                                <fmt:message key="catalog.tour.label.hotel_name"/>
                            </div>
                            <div class="col">${tour.hotelName}</div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <fmt:message key="catalog.label.hotel_ranking"/>
                            </div>
                            <div class="col">
                                <p class="h5">
                                    <c:if test="${tour.hotelRating>=3}">⭐⭐⭐</c:if><c:if test="${tour.hotelRating>=4}">⭐</c:if><c:if test="${tour.hotelRating>=5}">⭐</c:if>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:if>
                <hr>
                <p class="h3">
                    <fmt:message key="catalog.tour.label.schedule"/>
                </p>
                <div class="ms-0">
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
                <hr>
                <p class="h3">
                    <fmt:message key="catalog.tour.available_tickets"/> ${tour.ticketCount-tour.registeredTicketsCount-tour.paidTicketsCount}
                </p>
            </div>
        </div>
        <div class="card bg-white border-0 rounded-0 shadow-sm">
            <div class="card-body">
                <p class="card-title h5 fw-bold">
                    <fmt:message key="catalog.tour.label.book"/>
                </p>
                <div class="card-text">
                    Price per ticket: $${tour.ticketPrice}
                </div>
                <hr>
                <c:choose>
                    <c:when test="${tour.type=='vacation'}">
                            <p class="col-auto mb-1">
                                Total price for ${tour.ticketCount} tickets: ${tour.ticketCount*tour.ticketPrice}
                            </p>
                            <form method="post" action="/tour-agency">
                                <input type="hidden" name="command" value="book_tour"/>
                                <input type="hidden" name="ticket_count" value="${tour.ticketCount}"/>
                                <input type="hidden" name="total_price" value="${tour.ticketCount*tour.ticketPrice}"/>
                                <input type="hidden" name="tour_id" value="${tour.id}"/>
                                <input class="w-auto btn btn-primary border-0 rounded-0 shadow-sm" type="submit" name="book" value="<fmt:message key="catalog.tour.label.book"/>"/>
                            </form>
                    </c:when>
                    <c:otherwise>
                        <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                            <div class="row">
                                <div class="input-group w-100 border-0 rounded-0">
                                    <span class="input-group-text">Tickets count:</span>
                                    <button class="btn btn-outline-secondary" id="minus" onclick="subtractCounter()" type="button" disabled>-</button>
                                    <span class="input-group-text" type="text" id="ticket_count" onchange="setCountInput()">1</span>
                                    <button class="btn btn-outline-secondary" id="plus" onclick="appendCounter()" type="button">+</button>
                                </div>
                            </div>
                        </div>
                        <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                            <div class="row">
                                <div class="col-auto text-center">
                                    <span>Discount:&nbsp;</span>
                                    <span id="total_discount">0%</span>
                                </div>
                            </div>
                        </div>
                        <form method="post" action="/tour-agency">
                        <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                            <div class="row">
                                <div class="col-auto text-center">
                                    <span>Total price:&nbsp;</span>
                                    <span id="total_price">$${tour.ticketPrice}</span>
                                </div>
                            </div>
                        </div>
                            <input type="hidden" name="command" value="book_tour"/>
                            <input type="hidden" id="ticket_count_input" name="ticket_count" value="1"/>
                            <input type="hidden" id="total_price_hidden" name="total_price"/>
                            <input type="hidden" name="tour_id" value="${tour.id}"/>
                            <input class="w-auto btn btn-primary border-0 rounded-0 shadow-sm" type="submit" name="book" value="<fmt:message key="catalog.tour.label.book"/>"/>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
</div>
<script>
    const availableTickets = ${tour.ticketCount}-${tour.registeredTicketsCount}-${tour.paidTicketsCount};
    const currencyFormatter = Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
    });
    function setCountInput(value) {
        document.getElementById("ticket_count_input").value=value;
    }
    function subtractCounter() {
        let value = parseInt(document.getElementById("ticket_count").innerHTML);
        if (value>1) {
            value--;
            document.getElementById("ticket_count").innerHTML=value.toString();
        }
        updateTotalPrice(value);
        updateDisabledStatus(value);
        setCountInput(value);

    }

    function appendCounter() {
        let value = parseInt(document.getElementById("ticket_count").innerHTML);
        if (value<availableTickets) {
            value++;
            document.getElementById("ticket_count").innerHTML=value.toString();
        }
        updateTotalPrice(value);
        updateDisabledStatus(value);
        setCountInput(value);
    }

    function updateTotalPrice(value) {
        let discount=0;
        if (parseInt(${tour.discountPerEveryTicketsCount})!==0) {
            discount = Math.floor(value/${tour.discountPerEveryTicketsCount})*${tour.discountAmount};
            if (discount > ${tour.maxDiscount}) discount=${tour.maxDiscount};
        }
        document.getElementById("total_discount").innerHTML=discount+'%';
        document.getElementById("total_price").innerHTML=currencyFormatter.format(parseFloat(${tour.ticketPrice})*value/100*(100-discount)).toString();
        document.getElementById("total_price_hidden").value=parseFloat(${tour.ticketPrice})*value/100*(100-discount);
    }

    function updateDisabledStatus(value) {
        document.getElementById("minus").disabled = value === 1;
        document.getElementById("plus").disabled = value === ${tour.ticketCount};
    }
</script>
</body>
</html>

