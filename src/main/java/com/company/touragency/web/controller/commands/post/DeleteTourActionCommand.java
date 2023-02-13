package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.OrderService;
import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.TOUR_ID;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.CATALOG_PAGE;

public class DeleteTourActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an employee
        return role.equals(ADMIN);
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        ServletContext context = request.getServletContext();
        ServiceContainer container = (ServiceContainer) context.getAttribute(SERVICE_CONTAINER);
        OrderService orderService = container.getOrderService(context);
        TourService tourService = container.getTourService(context);
        //Parse tour id
        int tourId = Integer.parseInt(request.getParameter(TOUR_ID));
        //Remove all orders related to tour
        boolean success = orderService.removeAllOrdersWithTourId(tourId);
        //Remove all localisation related to tour
        success = success && tourService.removeAllLocales(tourId);
        //Remove tour
        success = success && tourService.removeTour(tourId);
        return success;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, CATALOG_PAGE);
    }
}
