<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>
<div class="position-absolute start-50 translate-middle-x" style="z-index:5000">
    <c:forEach items="${sessionScope.messages}" var="message">
        <div class="alert alert-dismissible shadow-sm rounded-0 border-0
                <c:choose>
                    <c:when test="${message.type=='INFO'}">
                        alert-info
                    </c:when>
                    <c:when test="${message.type=='ERROR'}">
                        alert-danger
                    </c:when>
                </c:choose>">
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        <fmt:message key="${message.messageCode}"/>
        </div>
    </c:forEach>
</div>