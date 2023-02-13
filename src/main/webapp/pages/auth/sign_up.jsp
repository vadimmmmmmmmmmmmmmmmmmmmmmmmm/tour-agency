<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>
<body class="vw-100 vh-100 mh-100 mw-100 p-3 bg-transparent">
<jsp:include page="/pages/messages.jsp"/>
<section class="w-100 h-100 d-flex justify-content-center align-items-center">
    <div class="card border-0 rounded-0 bg-white shadow-sm">
        <div class="card-body">
            <div class="card-title h5 fw-bold">
                <fmt:message key="auth.sign_up.label.registration"/>
            </div>
            <div class="row">
                <div class="col">
                    <cstm:localeSelector locale="${sessionScope.locale}" type="radio"/>
                </div>
            </div>
            <div class="card-text">
                <fmt:message key="auth.sign_up.label.name_surname_as_in_international_passport"/>
            </div>
            <form method="post">
                <div class="row">
                    <div class="col-auto">
                        <label class="form-label" for="firstName">
                            <fmt:message key="auth.sign_up.label.name"/>
                        </label>
                        <input type="text" id="firstName" class="form-control " name="name"/>
                    </div>
                    <div class="col-auto">
                        <label class="form-label" for="lastName">
                            <fmt:message key="auth.sign_up.label.surname"/>
                        </label>
                        <input type="text" id="lastName" class="form-control " name="surname"/>
                    </div>
                </div>
                <label class="form-label" for="emailInput">
                    <fmt:message key="auth.label.email"/>
                </label>
                <input type="email" id="emailInput" class="form-control" name="email"/>
                <label class="form-label" for="passwordInput">
                    <fmt:message key="auth.label.password"/>
                </label>
                <input type="password" id="passwordInput" class="form-control" name="password"/>
                <input type="hidden" id="command" name="command" value="sign_up"/>
                <input class="btn btn-primary w-100 rounded-0 border-0 mt-4" type="submit"
                       value="<fmt:message key="auth.label.sign_up"/>"/>
            </form>
            <hr>
            <div class="container-fluid bg-light rounded-0 p-3 mb-1">
                <div class="row">
                    <div class="col-auto text-center">
                        <span>
                            <fmt:message key="auth.label.have_account"/>&nbsp;
                        </span>
                        <a class="w-auto btn btn-secondary rounded-0 border-0" href="/tour-agency/sign_in">
                            <fmt:message key="auth.label.sign_in"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
