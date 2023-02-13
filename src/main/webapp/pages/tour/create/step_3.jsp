<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
        <fmt:message key="catalog.tour.create.label.fill_in_tour_info"/>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>
<body class="vw-100 vh-100 mh-100 mw-100 p-3 bg-transparent">
<jsp:include page="/pages/messages.jsp"/>
<section id="step_1" class="w-100 container-fluid">
    <p class="h3">
        <fmt:message key="catalog.label.create_tour"/>
    </p>
    <p class="h5">
        <fmt:message key="catalog.tour.create.label.fill_in_tour_info"/>
    </p>
    <form method="post">
        <div class="row mb-2">
            <div class="col-auto">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="enLocale" name="supported_language" value="en"/>
                    <label for="enLocale">🇺🇸</label>
                </div>
                <div class="form-group">
                    <label for="enTitle">
                        <fmt:message key="catalog.tour.create.label.title"/>
                    </label>
                    <input class="form-control" type="text" id="enTitle" name="tour_title_en"/>
                </div>
                <div class="form-group">
                    <label for="enDescription">
                        <fmt:message key="catalog.tour.create.label.description"/>
                    </label>
                    <textarea class="form-control" id="enDescription" rows="3" name="tour_desc_en"></textarea>
                </div>
            </div>
            <div class="col-auto">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="uaLocale" name="supported_language" value="ua"/>
                    <label for="uaLocale">🇺🇦</label>
                </div>
                <div class="form-group">
                    <label for="enTitle">
                        <fmt:message key="catalog.tour.create.label.title"/>
                    </label>
                    <input class="form-control"  type="text" id="uaTitle" name="tour_title_ua"/>
                </div>
                <div class="form-group">
                    <label for="uaDescription">
                        <fmt:message key="catalog.tour.create.label.description"/>
                    </label>
                    <textarea class="form-control" id="uaDescription" rows="3" name="tour_desc_ua"></textarea>
                </div>
            </div>
            <div class="col-auto">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="ruLocale" name="supported_language" value="ru"/>
                    <label for="ruLocale">🇷🇺</label>
                </div>
                <div class="form-group">
                    <label for="enTitle">
                        <fmt:message key="catalog.tour.create.label.title"/>
                    </label>
                    <input class="form-control"  type="text" id="ruTitle" name="tour_title_ru"/>
                </div>
                <div class="form-group">
                    <label for="ruDescription">
                        <fmt:message key="catalog.tour.create.label.description"/>
                    </label>
                    <textarea class="form-control" id="ruDescription" rows="3" name="tour_desc_ru"></textarea>
                </div>
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-auto">
                <div class="form-group-inline">
                    <label for="departureTakeoffDate">
                        <fmt:message key="catalog.tour.label.departure_ticket_date"/>
                    </label>
                    <input type="date" id="departureTakeoffDate" name="tour_departure_takeoff_date">
                </div>
            </div>
            <div class="col-auto">
                <div class="form-group-inline">
                    <label for="departureTakeoffDate">
                        <fmt:message key="catalog.tour.label.departure_ticket_time"/>
                    </label>
                    <input type="time" id="departureTakeoffTime" name="tour_departure_takeoff_time">
                </div>
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-auto">
                <div class="form-group-inline">
                    <label for="returnTakeoffDate">
                        <fmt:message key="catalog.tour.label.return_ticket_date"/>
                    </label>
                    <input type="date" id="returnTakeoffDate" name="tour_return_takeoff_date">
                </div>
            </div>
            <div class="col-auto">
                <div class="form-group-inline">
                    <label for="returnTakeoffTime">
                        <fmt:message key="catalog.tour.label.return_ticket_time"/>
                    </label>
                    <input type="time" id="returnTakeoffTime" name="tour_return_takeoff_time">
                </div>
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-auto">
                <div class="form-group">
                    <label for="ticketsCount">
                        <fmt:message key="users.profile.order.label.ticket_count"/>
                    </label>
                    <input type="number" min="1" max="500" id="ticketsCount" name="tour_ticket_count">
                </div>
            </div>
            <div class="col-auto">
                <div class="form-group">
                    <label for="ticketPrice">
                        <fmt:message key="catalog.tour.create.label.ticket_price"/>
                    </label>
                    <input type="text" min="1.0" id="ticketPrice" name="tour_ticket_price">
                </div>
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-auto">
                <div class="form-group">
                    <label for="discountAmount">
                        <fmt:message key="catalog.tour.create.label.discount_amount"/>
                    </label>
                    <input type="text" min="1" max="500" id="discountAmount" name="tour_discount_amount">
                </div>
            </div>
            <div class="col-auto">
                <div class="form-group">
                    <label for="discountPer">
                        <fmt:message key="catalog.tour.create.label.discount_per"/>
                    </label>
                    <input type="text" min="1" max="500" id="discountPer" name="tour_discount_per">
                </div>
            </div>
            <div class="col-auto">
                <div class="form-group">
                    <label for="discountMax">
                        <fmt:message key="catalog.tour.create.label.discount_max"/>
                    </label>
                    <input type="text" min="1" max="500" id="discountMax" name="tour_discount_max">
                </div>
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-auto">
                <label for="typeSelect">
                    <fmt:message key="catalog.tour.create.label.tour_type"/>
                </label>
                <div class="form-group" id="typeSelect">
                    <select class="form-select" name="tour_type">
                        <option value="vacation">
                            <fmt:message key="catalog.label.vacation"/>
                        </option>
                        <option value="excursion">
                            <fmt:message key="catalog.label.excursion"/>
                        </option>
                        <option value="shopping">
                            <fmt:message key="catalog.label.shopping"/>
                        </option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row mb-2">
            <div class="col-auto">
                <input type="hidden" name="command" value="set_new_tour_info">
                <input class="btn btn-primary btn-lg" type="submit" value="<fmt:message key="catalog.label.apply"/>"/>
            </div>
        </div>
    </form>
</section>
<script>
    document.getElementById("departureTakeoffDate").min = new Date().toLocaleDateString('en-ca');
    document.getElementById("returnTakeoffDate").min = new Date().toLocaleDateString('en-ca');
</script>
</body>
</html>
