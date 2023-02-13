package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.HotelService;
import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.entities.bean.TourCreationProperties;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.TOUR_CREATE_PROPERTIES;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.CREATE_TOUR_PAGE;

public class SetNewTourHotelActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an admin
        return role.equals(ADMIN);
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        ServletContext context = request.getServletContext();
        ServiceContainer container = (ServiceContainer) context.getAttribute(SERVICE_CONTAINER);
        TourCreationProperties properties = (TourCreationProperties) request.getSession().getAttribute(TOUR_CREATE_PROPERTIES);
        int hotelSelection = Integer.parseInt(request.getParameter(HOTEL_SELECTION));
        if (hotelSelection==0) {
            properties.setHotelId(0);
        }
        if (hotelSelection==1) {
            properties.setHotelId(Integer.parseInt(request.getParameter(HOTEL)));
        }
        if (hotelSelection==2) {
            HotelService hotelService = container.getHotelService(context);
            String englishName = request.getParameter(HOTEL_TITLE_EN);
            String ukrainianName = request.getParameter(HOTEL_TITLE_UA);
            String russianName = request.getParameter(HOTEL_TITLE_RU);
            int starRating = Integer.parseInt(request.getParameter(HOTEL_STAR_RATING));
            int hotelId = hotelService.addHotelToCity(properties.getCityId(), starRating);
            hotelService.setHotelNameLocalisation(hotelId, englishName, ukrainianName, russianName);
            properties.setHotelId(hotelId);
        }
        request.getSession().setAttribute(TOUR_CREATE_PROPERTIES, properties);
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, CREATE_TOUR_PAGE+"?step="+3);
    }
}
