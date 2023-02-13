package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.db.service.OrderService;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.CUSTOMER;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.CURRENT_USER;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.*;

public class BookTourActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user's role is not customer
        return role.equals(CUSTOMER);
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        //get OrderService from ServletContext
        ServletContext context = request.getServletContext();
        OrderService service = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getOrderService(context);

        //get order info from jsp form
        int tourId = Integer.parseInt(request.getParameter(TOUR_ID));
        int ticketCount = Integer.parseInt(request.getParameter(TICKET_COUNT));
        int userId = ((User) request.getSession().getAttribute(CURRENT_USER)).getId();
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(request.getParameter(TOTAL_PRICE)));

        //return whether the order was registered successfully
        return service.register(userId, tourId, ticketCount, price);
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        //redirect to user's profile page
        return new Reroute(REDIRECT, PROFILE_PAGE);
    }
}
