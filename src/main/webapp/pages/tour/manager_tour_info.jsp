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
            <c:url value="../../img/tour-img/${tour.imageFile}" var="imagePath"/>
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
    <div class="card w-100 bg-white border-0 rounded-0 shadow-sm flex-sm-grow-1">
        <div class="card-body">
            <form method="post">
                <input type="hidden" name="tour_id" value="${tour.id}"/>
                <input type="hidden" name="command" value="change_on_fire_status"/>
                <c:choose>
                    <c:when test="${tour.onFire==true}">
                        <input type="hidden" name="on_fire" value="true"/>
                        <input class="btn btn-secondary w-100 rounded-0 border-0" type="submit" value="<fmt:message key="catalog.label.remove_on_fire_status"/>">
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="on_fire" value="false"/>
                        <input class="btn btn-danger w-100 rounded-0 border-0" type="submit" value="<fmt:message key="catalog.label.set_on_fire_status"/>">
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </div>
    <c:set var="supportedLanguages" value="${requestScope.tour_supported_languages}"/>
    <c:if test="${supportedLanguages.containsKey('en')}">
    <div class="card w-auto bg-white border-0 rounded-0 shadow-sm " style="max-width: 380px;">
        <div class="card-body">
            <div class="card-title h3">🇺🇸</div>

                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col text-center">
                                <span>
                                    <fmt:message key="catalog.tour.create.label.title"/>
                                </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                                <span>
                                        ${supportedLanguages.get('en').title}
                                </span>
                        </div>
                    </div>
                </div>
                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col text-center">
                                <span>
                                    <fmt:message key="catalog.tour.create.label.description"/>
                                </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                                <span>
                                        ${supportedLanguages.get('en').description}
                                </span>
                        </div>
                    </div>
                </div>

        </div>
    </div>
    </c:if>
    <c:if test="${supportedLanguages.containsKey('ua')}">
    <div class="card w-auto bg-white border-0 rounded-0 shadow-sm" style="max-width: 380px;">
        <div class="card-body">
            <div class="card-title h3">🇺🇦</div>

                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col text-center">
                                <span>
                                    <fmt:message key="catalog.tour.create.label.title"/>
                                </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                                <span>
                                        ${supportedLanguages.get('ua').title}
                                </span>
                        </div>
                    </div>
                </div>
                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col text-center">
                                <span>
                                    <fmt:message key="catalog.tour.create.label.description"/>
                                </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                                <span>
                                        ${supportedLanguages.get('ua').description}
                                </span>
                        </div>
                    </div>
                </div>

        </div>
    </div>
    </c:if>
    <c:if test="${supportedLanguages.containsKey('ru')}">
    <div class="card w-auto bg-white border-0 rounded-0 shadow-sm" style="max-width: 380px;">
        <div class="card-body">
            <div class="card-title h3">🇷🇺</div>

                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col text-center">
                                <span>
                                    <fmt:message key="catalog.tour.create.label.title"/>
                                </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                                <span>
                                        ${supportedLanguages.get('ru').title}
                                </span>
                        </div>
                    </div>
                </div>
                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col text-center">
                                <span>
                                    <fmt:message key="catalog.tour.create.label.description"/>
                                </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                                <span>
                                        ${supportedLanguages.get('ru').description}
                                </span>
                        </div>
                    </div>
                </div>

        </div>
    </div>
    </c:if>
    <c:if test="${tour.hotelName!='-'}">
    <div class="card w-auto bg-white border-0 rounded-0 shadow-sm mb-2">
        <div class="card-body">
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
        </div>
    </div>
    </c:if>
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
    <div class="card w-auto bg-white border-0 rounded-0 shadow-sm">
        <div class="card-body">
            <div class="card-title">
                <fmt:message key="catalog.tour.edit.label.discount_properties"/>
            </div>
            <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                <div class="row">
                    <div class="col text-center">
                        <span>
                            <fmt:message key="catalog.tour.create.label.discount_amount"/>
                        </span>
                        <span>
                            &nbsp;${tour.discountAmount}
                        </span>
                        <span>
                            %
                        </span>
                    </div>
                </div>
            </div>
            <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                <div class="row">
                    <div class="col text-center">
                        <span>
                            <fmt:message key="catalog.tour.create.label.discount_per"/>
                        </span>
                        <span>
                            &nbsp;${tour.discountPerEveryTicketsCount}
                        </span>
                        <span>
                            <i class="fa-solid fa-ticket"></i>
                        </span>
                    </div>
                </div>
            </div>
            <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                <div class="row">
                    <div class="col text-center">
                        <span>
                            <fmt:message key="catalog.tour.create.label.discount_max"/>
                        </span>
                        <span>
                            &nbsp;${tour.maxDiscount}
                        </span>
                        <span>
                            %
                        </span>

                    </div>
                </div>
            </div>
            <hr>
            <div class="card-title">
                <fmt:message key="catalog.tour.edit.label.set_new"/>
            </div>
            <form method="post">
                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col">
                            <span>
                                <fmt:message key="catalog.tour.create.label.discount_amount"/>&nbsp;
                            </span>
                            <input class="form-control-sm rounded-0" type="text" name="discount" id="discountAmount" style="width: 3rem;"/>%
                        </div>
                    </div>
                </div>
                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col">
                            <span>
                                <fmt:message key="catalog.tour.create.label.discount_per"/>&nbsp;
                            </span>

                            <input class="form-control-sm rounded-0" type="text" name="discount_per_every" id="discountPerEvery" style="width: 3rem;"/>
                        </div>
                    </div>
                </div>
                <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                    <div class="row">
                        <div class="col">
                            <span>
                                <fmt:message key="catalog.tour.create.label.discount_max"/>&nbsp;
                            </span>
                            <input class="form-control-sm rounded-0" type="text" name="discount_max" id="maxPossibleDiscount" style="width: 3rem;"/>%
                        </div>
                    </div>
                </div>
                <input type="hidden" name="tour_id" value="${tour.id}"/>
                <input type="hidden" name="command" value="set_tour_discount"/>
                <hr>
                <input class="btn btn-primary border-0 rounded-0" type="submit" value="<fmt:message key="catalog.label.set_tour_discount"/>"/>
            </form>
        </div>
    </div>
</section>
</body>
</html>