<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale"/>
<c:choose>
    <c:when test="${sessionScope.current_user!=null}">
        <c:set var="role" value="${sessionScope.current_user.status}"/>
    </c:when>
    <c:otherwise>
        <c:set var="role" value="0"/>
    </c:otherwise>
</c:choose>
<jsp:include page="/pages/messages.jsp"/>
<nav class="navbar navbar-expand-md navbar-light ps-0 pe-0">
    <div class="container-fluid ps-0 pe-0 align-items-stretch justify-content-start gap-2">
        <button class="navbar-toggler bg-white rounded-0 border-0 shadow-sm" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <c:if test="${role==0}">
            <cstm:localeSelector locale="${sessionScope.locale}" type="dropdown"/>
        </c:if>
        <form class="w-100" method="post" action="/tour-agency/">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <div class="navbar-nav me-auto mb-2 mb-lg-0">
                    <c:if test="${role==0}">
                        <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/catalog">
                            <fmt:message key="nav.label.catalog"/>
                        </a>
                        <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/sign_in">
                            <fmt:message key="nav.label.sign_in"/>
                        </a>
                    </c:if>
                    <c:if test="${role==1}">
                        <form class="w-100" method="post" action="/tour-agency/">
                            <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/catalog">
                                <fmt:message key="nav.label.catalog"/>
                            </a>
                            <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/profile">
                                <fmt:message key="nav.label.profile"/>
                            </a>
                            <input type="hidden" name="command" value="sign_out"/>
                            <input type="submit" class="nav-link btn btn-link btn-lg" name="sign_out" value="<fmt:message key="nav.label.sign_out"/>"/>
                        </form>
                    </c:if>
                    <c:if test="${role==2}">
                        <form class="w-100" method="post">
                            <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/catalog">
                                <fmt:message key="nav.label.catalog"/>
                            </a>
                            <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/bookings">
                                <fmt:message key="nav.label.customer_orders"/>
                            </a>
                            <input type="hidden" name="command" value="sign_out"/>
                            <input type="submit" class="nav-link btn btn-link btn-lg" name="sign_out" value="<fmt:message key="nav.label.sign_out"/>"/>
                        </form>
                    </c:if>
                    <c:if test="${role==3}">
                        <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/catalog">
                            <fmt:message key="nav.label.catalog"/>
                        </a>
                        <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/users">
                            <fmt:message key="nav.label.users"/>
                        </a>
                        <a class="nav-link btn btn-link btn-lg" aria-current="page" href="/tour-agency/bookings">
                            <fmt:message key="nav.label.customer_orders"/>
                        </a>
                        <input type="hidden" id="command" name="command" value="sign_out"/>
                        <input type="submit" class="nav-link btn btn-link btn-lg" name="sign_out" value="<fmt:message key="nav.label.sign_out"/>"/>
                    </c:if>
                </div>
            </div>
        </form>
    </div>
</nav>