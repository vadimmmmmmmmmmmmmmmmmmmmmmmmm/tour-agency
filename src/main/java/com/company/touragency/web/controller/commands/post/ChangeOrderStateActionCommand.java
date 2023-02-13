package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.OrderService;
import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.*;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.ORDER_ID;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.ORDER_STATE;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.CUSTOMERS_BOOKINGS_PAGE;

public class ChangeOrderStateActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an employee
        return role>CUSTOMER;
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        ServletContext context = request.getServletContext();

        ServiceContainer container = (ServiceContainer)context.getAttribute(SERVICE_CONTAINER);
        OrderService orderService = container.getOrderService(context);
        TourService tourService = container.getTourService(context);

        int orderId = Integer.parseInt(request.getParameter(ORDER_ID));
        String newState = request.getParameter(ORDER_STATE);
        boolean success = orderService.changeOrderState(orderId, newState);
        int tourId = orderService.getOrderTourId(orderId);

        if (Objects.equals(newState, "paid")) {
            success &= tourService.increaseRating(tourId);
        } else {
            success &= tourService.decreaseRating(tourId);
        }

        return success;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, CUSTOMERS_BOOKINGS_PAGE);
    }
}
