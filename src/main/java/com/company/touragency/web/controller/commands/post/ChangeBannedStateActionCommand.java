package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.OrderService;
import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.db.service.UserService;
import com.razkuuuuuuu.touragency.entities.entity.Tour;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.USER_ID;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.USER_STATUS;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.USERS_PAGE;

public class ChangeBannedStateActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an admin
        return role.equals(ADMIN);
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        //get UserService from ServletContext
        ServletContext context = request.getServletContext();
        UserService service = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getUserService(context);

        //get user's id and their banned status
        int currentStatus = Integer.parseInt(request.getParameter(USER_STATUS));
        int userId = Integer.parseInt(request.getParameter(USER_ID));

        //if command's action is to ban, cancel all user's orders
        if (currentStatus==1) {
            ServiceContainer container = (ServiceContainer)context.getAttribute(SERVICE_CONTAINER);
            OrderService orderService = container.getOrderService(context);
            TourService tourService = container.getTourService(context);
            tourService.decreaseRatingForAllUserBookings(userId);
            orderService.cancelAllUserRegisteredOrders(userId);
        }
        //return whether user was banned
        return service.setUserBannedState(userId, currentStatus==1 ? -1 : 1);
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, USERS_PAGE);
    }
}
