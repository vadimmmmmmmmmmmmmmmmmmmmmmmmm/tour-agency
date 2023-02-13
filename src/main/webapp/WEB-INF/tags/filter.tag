<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="catalogProperties" type="java.lang.String" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="cstm" uri="/WEB-INF/custom.tld"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="locale"/>
<form method="POST" action="${pageContext.request.contextPath}">
<div class="container-fluid w-100 bg-white rounded-0 shadow-sm p-2 mb-2">
    <div class="row align-items-center justify-content-between">
        <div class="col-auto">
            <div class="row align-items-center">
                <div class="col-auto ">
                    <span class="nav-item">
                        <fmt:message key="catalog.label.filter"/>
                    </span>
                </div>
                <div class="col-auto">
                    <div class="form-check-inline">
                        <input class="form-check-input"
                               type="checkbox"
                               id="excursionCheck"
                               name="excursion_selected"
                               value="1"
                               <c:if test="${(fn:contains(catalogProperties, 'excursion')) or (fn:contains(catalogProperties, 'all'))}">checked</c:if>/>
                        <label class="form-check-label" for="excursionCheck">
                            <fmt:message key="catalog.label.excursion"/>
                        </label>
                    </div>
                </div>
                <div class="col-auto">
                    <div class="form-check-inline">
                        <input class="form-check-input"
                               type="checkbox"
                               id="vacationCheck"
                               name="vacation_selected"
                               value="1"
                               <c:if test="${(fn:contains(catalogProperties, 'vacation')) or (fn:contains(catalogProperties, 'all'))}">checked</c:if>/>
                        <label class="form-check-label" for="vacationCheck">
                            <fmt:message key="catalog.label.vacation"/>
                        </label>
                    </div>
                </div>
                <div class="col-auto">
                    <div class="form-check-inline">
                        <input class="form-check-input"
                               type="checkbox"
                               id="shoppingCheck"
                               name="shopping_selected"
                               value="1"
                               <c:if test="${(fn:contains(catalogProperties, 'shopping')) or (fn:contains(catalogProperties, 'all'))}">checked</c:if>/>
                        <label class="form-check-label" for="shoppingCheck">
                            <fmt:message key="catalog.label.shopping"/>
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-auto">
            <div class="row">
                <div class="col-auto d-flex align-items-center">
                    <span class="nav-item">
                        <fmt:message key="catalog.label.sorted_by"/>
                    </span>
                </div>
                <div class="col-auto">
                    <select class="form-select rounded-0 border-0" name="sort_condition">
                        <option value="0" <c:if test="${fn:contains(catalogProperties, '%3A0')}">selected</c:if>>
                            <p>
                                ↑ <fmt:message key="catalog.label.alphabetic_order"/>
                            </p>
                        </option>
                        <option value="1" <c:if test="${fn:contains(catalogProperties, '%3A1')}">selected</c:if>>
                            <p>
                                ↑ <fmt:message key="catalog.label.price"/>
                            </p>
                        </option>
                        <option value="2" <c:if test="${fn:contains(catalogProperties, '%3A2')}">selected</c:if>>
                            <p>
                                ↓ <fmt:message key="catalog.label.price"/>
                            </p>
                        </option>
                        <option value="3" <c:if test="${fn:contains(catalogProperties, '%3A3')}">selected</c:if>>
                            <p>
                                ↑ <fmt:message key="catalog.label.hotel_ranking"/>
                            </p>
                        </option>
                        <option value="4" <c:if test="${fn:contains(catalogProperties, '%3A4')}">selected</c:if>>
                            <p>
                                ↓ <fmt:message key="catalog.label.hotel_ranking"/>
                            </p>
                        </option>
                        <option value="5" <c:if test="${fn:contains(catalogProperties, '%3A5')}">selected</c:if>>
                            <p>
                                ↑ <fmt:message key="catalog.label.people_count"/>
                            </p>
                        </option>
                        <option value="6" <c:if test="${fn:contains(catalogProperties, '%3A6')}">selected</c:if>>
                            <p>
                                ↓ <fmt:message key="catalog.label.people_count"/>
                            </p>
                        </option>
                    </select>
                </div>
                <div class="col-auto">
                    <input type="hidden" name="command" value="set_catalog_properties"/>
                    <input class="w-100 btn btn-primary rounded-0 border-0" type="submit" value="<fmt:message key="catalog.label.apply"/>"/>
                </div>
            </div>
        </div>
    </div>
</div>
</form>