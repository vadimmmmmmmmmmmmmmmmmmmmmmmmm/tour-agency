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
        <fmt:message key="catalog.tour.create.label.select_hotel"/>
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
        <fmt:message key="catalog.tour.create.label.select_hotel"/>
    </p>
    <form method="post">
        <div class="row">
            <div class="col-auto">
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="noHotel" name="hotel_selection" value="0"/>
                    <label class="form-check-label" for="noHotel">
                        <fmt:message key="catalog.tour.create.label.no_hotel"/>
                    </label>
                </div>
            </div>
            <div class="col-auto">
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="existingHotel" name="hotel_selection" value="1"/>
                    <label class="form-check-label" for="existingHotel">
                        <fmt:message key="catalog.tour.create.label.hotel_from_database"/>
                    </label>
                    <select class="form-select" name="hotel">
                        <c:forEach items="${requestScope.hotel_list}" var="hotel" >
                            <option value="${hotel.id}">${hotel.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-auto">
                <div class="form-check">
                    <input class="form-check-input" type="radio" id="newHotel" name="hotel_selection" value="2"/>
                    <label class="form-check-label" for="newHotel">
                        <fmt:message key="catalog.tour.create.label.add_new_hotel"/>
                    </label>
                </div>
                <div class="w-auto">
                    <div class="form-group">
                        <label class="form-label" for="hotelTitleEn">
                            <fmt:message key="catalog.tour.create.label.hotel_name_en"/>
                        </label>
                        <input type="text" id="hotelTitleEn" class="form-control " name="hotel_title_en"/>
                    </div>
                    <div class="form-group-inline">
                        <label class="form-label" for="tourTitle">
                            <fmt:message key="catalog.tour.create.label.hotel_name_ua"/>
                        </label>
                        <input type="text" id="hotelTitleUa" class="form-control " name="hotel_title_ua"/>
                    </div>
                    <div class="form-group-inline">
                        <label class="form-label" for="tourTitle">
                            <fmt:message key="catalog.tour.create.label.hotel_name_ru"/>
                        </label>
                        <input type="text" id="tourTitle" class="form-control " name="hotel_title_ru"/>
                    </div>
                    <div class="form-group-inline">
                        <label class="form-label" for="hotelTitleRu">
                            <fmt:message key="catalog.tour.create.label.hotel_star_rating"/>
                        </label>
                        <input type="text" id="hotelTitleRu" class="form-control " name="hotel_star_rating"/>
                    </div>
                </div>
            </div>
            <div class="col-auto">
                <input type="hidden" name="command" value="set_new_tour_hotel">
                <input class="btn btn-primary btn-lg" type="submit" value="<fmt:message key="catalog.label.apply"/>"/>
            </div>
        </div>
    </form>
</section>
</body>
</html>
