package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.db.service.OrderService;
import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.CUSTOMER;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.CUSTOMER_ORDERS_JSP;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.ORDERS;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;

public class CustomerBookingLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user isn't employee
        return role>CUSTOMER;
    }
    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) throws AppException {
        //get OrderService from servlet context
        ServletContext context = request.getServletContext();
        OrderService service = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getOrderService(context);

        //put unregistered orders list in requestScope
        request.setAttribute(ORDERS, service.getUnregisteredOrders());
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //load customer orders jsp
        return new Reroute(FORWARD, CUSTOMER_ORDERS_JSP);
    }
}
