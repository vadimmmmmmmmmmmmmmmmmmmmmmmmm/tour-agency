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
    <title>Users</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    <script src="https://kit.fontawesome.com/92df7a1271.js" crossorigin="anonymous"></script>
    <link href=" <c:url value="/css/background.css"/>" rel="stylesheet">
</head>
<body class="vw-100 mw-100 p-3 bg-transparent">
<cstm:navBar/>

<section id="Users" class="w-100 position-relative">
    <div class="w-100 h-auto">
        <a href="/tour-agency/users/add" class="btn btn-primary border-0 rounded-0 shadow-sm w-auto mb-2">
            <fmt:message key="users.label.add_employee"/>
        </a>
    </div>
    <div class="w-100">
        <div class="d-flex flex-wrap justify-content-start mb-2 gap-2">
            <c:forEach items="${requestScope.user_list}" var="user">
                <div class="card bg-white border-0 rounded-0 shadow-sm">
                    <div class="card-body">
                        <div class="card-title">
                                ${user.name}&nbsp;${user.surname}
                        </div>
                        <div class="container-fluid">
                            <div class="row bg-light rounded-0 p-3 mb-1">
                                <div class="col-auto text-center" style="width: 42px;">
                                    <i class="fa-solid fa-at"></i>
                                </div>
                                <div class="col-auto">
                                        ${user.email}
                                </div>
                            </div>
                            <div class="row mb-1 gap-1">
                                <c:choose>
                                    <c:when test="${user.status==-1}">
                                        <div class="col p-0">
                                            <div class="w-100 bg-danger text-center text-black p-3 rounded-0 shadow-sm">
                                                <fmt:message key="users.label.banned"/>
                                            </div>
                                        </div>
                                        <div class="col p-0">
                                            <form method="post">
                                                <input type="hidden"
                                                       name="command"
                                                       value="change_banned_state"
                                                />
                                                <input type="hidden"
                                                       name="user_id"
                                                       value="${user.id}"
                                                />
                                                <input type="hidden"
                                                       name="user_status"
                                                       value="-1"
                                                />
                                                <input class="w-100 p-3 btn btn-secondary h-auto border-0 rounded-0 shadow-sm"
                                                       type="submit"
                                                       value="unban"
                                                />
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:when test="${user.status==1}">
                                        <div class="col p-0">
                                            <div class="w-100 bg-secondary text-center text-black p-3 rounded-0 shadow-sm">
                                                <fmt:message key="users.label.customer"/>
                                            </div>
                                        </div>
                                        <div class="col p-0">
                                            <form method="post">
                                                <input type="hidden"
                                                       name="command"
                                                       value="change_banned_state"
                                                />
                                                <input type="hidden"
                                                       name="user_id"
                                                       value="${user.id}"
                                                />
                                                <input type="hidden"
                                                       name="user_status"
                                                       value="1"
                                                />
                                                <input class="w-100 p-3 btn btn-danger h-auto border-0 rounded-0 shadow-sm"
                                                       type="submit"
                                                       value="ban"
                                                />
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:when test="${user.status==2}">
                                        <div class="col p-0">
                                            <div class="w-100 bg-primary text-center text-black p-3 rounded-0 shadow-sm">
                                                <fmt:message key="users.label.manager"/>
                                            </div>
                                        </div>
                                        <div class="col p-0">
                                            <form method="post">
                                                <input type="hidden"
                                                       name="command"
                                                       value="remove_employee"
                                                />
                                                <input type="hidden"
                                                       name="user_id"
                                                       value="${user.id}"
                                                />
                                                <input class="w-100 p-3 btn btn-danger h-auto border-0 rounded-0 shadow-sm"
                                                       type="submit"
                                                       value="remove"
                                                />
                                            </form>
                                        </div>

                                    </c:when>
                                    <c:when test="${user.status==3}">
                                        <div class="bg-success text-center text-black p-3 rounded-0 w-100 shadow-sm">
                                            <fmt:message key="users.label.admin"/>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
</body>
</html>
