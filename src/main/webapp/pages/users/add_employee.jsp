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
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>
<body class="vw-100 vh-100 mh-100 mw-100 p-3 bg-transparent">
<cstm:navBar/>
<section class="h-auto">
    <div class="container h-auto w-auto p-0 m-0">
        <div class="row justify-content-start align-items-start h-auto">
            <div class="col-12 col-lg-9 col-xl-7">
                <div class="card border-0">
                    <div class="card-body p-4 p-md-5">
                        <div class="row mb-4 pb-2 pb-md-0 mb-md-5">
                            <div class="col-12">
                                <h3>
                                    <fmt:message key="auth.sign_up.label.registration"/>
                                </h3>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <p class="form-check-label">
                                    <fmt:message key="auth.sign_up.label.name_surname_as_in_international_passport"/>
                                </p>
                            </div>
                        </div>
                        <form method="post" action="/tour-agency/users/add">
                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <div class="form-group">
                                        <label class="form-label" for="firstName">
                                            <fmt:message key="auth.sign_up.label.name"/>
                                        </label>
                                        <input type="text" id="firstName" class="form-control " name="name"/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-group">
                                        <label class="form-label" for="lastName">
                                            <fmt:message key="auth.sign_up.label.surname"/>
                                        </label>
                                        <input type="text" id="lastName" class="form-control " name="surname"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group">
                                        <label class="form-label" for="emailInput">
                                            <fmt:message key="auth.label.email"/>
                                        </label>
                                        <input type="email" id="emailInput" class="form-control" name="email"/>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group">
                                        <label class="form-label" for="passwordInput">
                                            <fmt:message key="auth.label.password"/>
                                        </label>
                                        <input type="password" id="passwordInput" class="form-control" name="password"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <p class="form-check-label">
                                        <fmt:message key="users.label.employee_language"/>
                                    </p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="locale" value="ua" id="uaRadio">
                                            <label class="form-check-label" for="uaRadio">🇺🇦</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="locale" value="ru" id="ruRadio">
                                            <label class="form-check-label" for="ruRadio">🇷🇺</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="locale" value="en" id="enRadio" >
                                            <label class="form-check-label" for="enRadio">🇺🇸</label>
                                        </div>
                                </div>
                            </div>
                            <input type="hidden" id="command" name="command" value="add_employee"/>
                            <input class="btn btn-primary  mt-4 pt-2" type="submit" value="<fmt:message key="auth.label.sign_up"/>"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
