package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import com.razkuuuuuuu.touragency.entities.entity.Tour;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.PLACE_ORDER_JSP;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.CUSTOMER;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.TOUR;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.TOUR_ID;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.LOCALE;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;

public class BookTourLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user isn't customer
        return role.equals(CUSTOMER);
    }
    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) throws AppException {
        //get TourService object from servlet context;
        ServletContext context = request.getServletContext();
        TourService tourService = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getTourService(context);

        //get tour id from url
        int tourId = Integer.parseInt(request.getParameter(TOUR_ID));

        //get tour by id
        Tour tour = tourService.getByIdInUserLocale(tourId, (String) request.getSession().getAttribute(LOCALE));

        //trigger redirect to error page if tour doesn't have tickets available to book
        if (tour.getRegisteredTicketsCount()+tour.getPaidTicketsCount()==tour.getTicketCount()) {
            throw new AppException();
        }

        //add tour to requestScope to show on jsp
        request.setAttribute(TOUR, tour);
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //forward to book tour page
        return new Reroute(FORWARD, PLACE_ORDER_JSP);
    }
}
