package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.*;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.INVALID_DATA;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;

public class SetTourDiscountActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an employee
        return role>CUSTOMER;
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        int tourId, discount, perEvery, maxDiscount;
        try {
            discount = Integer.parseInt(request.getParameter(DISCOUNT));
            if (discount < 0 || discount > 100) throw new AppException();
            perEvery = Integer.parseInt(request.getParameter(DISCOUNT_PER_EVERY));
            if (perEvery < 0) throw new AppException();
            maxDiscount = Integer.parseInt(request.getParameter(DISCOUNT_MAX));
            if (maxDiscount < discount || maxDiscount > 100) throw new AppException();
            tourId = Integer.parseInt(request.getParameter(TOUR_ID));
        } catch (NumberFormatException e) {
            throw new AppException(INVALID_DATA);
        }
        ServletContext context = request.getServletContext();
        TourService service = ((ServiceContainer) context.getAttribute(SERVICE_CONTAINER)).getTourService(context);
        return service.setTourDiscount(tourId, discount, perEvery, maxDiscount);
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        StringBuilder destination = new StringBuilder(request.getRequestURI());
        String queryString = request.getQueryString();
        if (queryString!=null && !queryString.isEmpty()) {
            destination.append('?').append(queryString);
        }
        return new Reroute(REDIRECT, destination.toString());
    }
}
