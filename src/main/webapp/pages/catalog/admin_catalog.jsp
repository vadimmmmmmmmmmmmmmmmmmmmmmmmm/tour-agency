<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Catalog</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>
<body class="vw-100 vh-100 mh-100 mw-100 p-3 bg-transparent">
<cstm:navBar/>
<section id="Tours" class="w-100 position-relative">
    <div class="w-100 h-auto d-flex flex-row justify-content-between align-items-stretch gap-1">
        <div class="w-100">
            <cstm:catalogFilter catalogProperties="${filter}"/>
        </div>
        <a class="w-auto flex-shrink-1 text-nowrap btn btn-link text-decoration-none text-black bg-white border-0 rounded-0 shadow-sm h-100" href="/tour-agency/catalog/create?step=1">
            <fmt:message key="catalog.label.create_tour"/>
        </a>
    </div>
    <c:choose>
        <c:when test="${fn:length(requestScope.tour_list)<1}">
            <div class="alert alert-warning shadow-sm border-0 rounded-0" role="alert">
                <fmt:message key="catalog.label.no_tours_found"/>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${page_count>1}">
                <div class="w-auto mb-2 d-flex">
                    <div class="input-group-sm bg-white shadow-sm border-0 rounded w-auto ">
                        <c:forEach begin="1" end="${page_count}" var="page_number">
                            <c:choose>
                                <c:when test="${page eq page_number}">
                                    <a class="btn btn-light disabled" href="#">${page_number}</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-light" href="/tour-agency/catalog?page=${page_number}&filter=${filter}">
                                            ${page_number}
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <div class="w-100">
                <div class="d-flex flex-wrap justify-content-start mb-2 gap-2">
                    <c:forEach items="${requestScope.tour_list}" var="tour">
                            <div class="card bg-white border-0 rounded-0 shadow-sm container-fluid p-0 m-0" style="max-width: 480px">
                                <div class="row g-0">
                                    <div class="col" style="width:280px;">
                                        <div class="card-im d-block w-100 position-relative shadow-sm">
                                            <div class="ratio ratio-16x9">
                                                <c:url value="../../img/tour-img/${tour.imageFile}" var="imagePath"/>
                                                <img src="${imagePath}" alt="..."/>
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
                                            <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                                                <div class="row">
                                                    <div class="col-auto text-center" style="width: 42px;">
                                                        <i class="fa-solid fa-right-long"></i>
                                                    </div>
                                                    <div class="col-auto">
                                                            ${tour.departureTakeoffDate}
                                                    </div>
                                                    <div class="col-auto">
                                                        <fmt:formatDate value="${tour.departureTakeoffTime}" type="time" timeStyle = "short"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <c:if test="${tour.hotelName!='-'}">
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
                                            <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                                                <div class="row">
                                                    <div class="col-auto text-center" style="width: 42px;">
                                                        <i class="fa-solid fa-left-long"></i>
                                                    </div>
                                                    <div class="col-auto">
                                                            ${tour.returnTakeoffDate}
                                                    </div>
                                                    <div class="col-auto">
                                                        <fmt:formatDate value="${tour.returnTakeoffTime}" type="time" timeStyle = "short"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="container-fluid bg-light rounded-0 p-3">
                                                <div class="row">
                                                    <div class="col-auto text-center" style="width: 42px;">
                                                        <i class="fa-solid fa-dollar-sign"></i>
                                                    </div>
                                                    <div class="col-auto">
                                                        <span>${tour.ticketPrice}</span>
                                                        <span>
                                                            <fmt:message key="catalog.label.per_ticket"/>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="p-3 d-flex justify-content-between flex-column w-100">
                                            <div>
                                                <a class="btn btn-light text-black p-3 border-0 rounded-0 w-100 mb-2 text-decoration-none text-wrap"
                                                   href="/tour-agency/catalog/tour?id=${tour.id}">
                                                        ${tour.enTitle}
                                                </a>
                                                <span>${tour.tourRating}</span>
                                                <p class="bg-light text-decoration-none text-black p-3 rounded-0 w-100 text-wrap h-auto">
                                                        ${tour.enDescription}
                                                </p>
                                            </div>
                                            <hr>
                                            <div>
                                                <a class="btn btn-light text-black p-3 border-0 rounded-0 w-100 mb-2 text-decoration-none text-wrap"
                                                   href="/tour-agency/catalog/tour?id=${tour.id}">
                                                        ${tour.uaTitle}
                                                </a>
                                                <p class="bg-light text-decoration-none text-black p-3 rounded-0 w-100 text-wrap h-auto">
                                                        ${tour.uaDescription}
                                                </p>
                                            </div>
                                            <hr>
                                            <div>
                                                <a class="btn btn-light text-black p-3 border-0 rounded-0 w-100 mb-2 text-decoration-none text-wrap"
                                                   href="/tour-agency/catalog/tour?id=${tour.id}">
                                                        ${tour.ruTitle}
                                                </a>
                                                <p class="bg-light text-decoration-none text-black p-3 rounded-0 w-100 text-wrap h-auto">
                                                        ${tour.ruDescription}
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                    </c:forEach>
                </div>
            </div>
            <c:if test="${page_count>1}">
                <div class="w-auto mb-2 d-flex">
                    <div class="input-group-sm bg-white shadow-sm border-0 rounded w-auto ">
                        <c:forEach begin="1" end="${page_count}" var="page_number">
                            <c:choose>
                                <c:when test="${page eq page_number}">
                                    <a class="btn btn-light disabled" href="#">${page_number}</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-light" href="/tour-agency/catalog?page=${page_number}&filter=${filter}">
                                            ${page_number}
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</section>
</body>
</html>