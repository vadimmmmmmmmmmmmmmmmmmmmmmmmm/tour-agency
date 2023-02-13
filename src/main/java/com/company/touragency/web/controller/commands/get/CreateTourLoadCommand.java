package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.db.service.CityService;
import com.razkuuuuuuu.touragency.db.service.HotelService;
import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.entities.bean.TourCreationProperties;
import com.razkuuuuuuu.touragency.entities.entity.City;
import com.razkuuuuuuu.touragency.entities.entity.Hotel;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.LOCALE;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.TOUR_CREATE_PROPERTIES;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.ERROR_PAGE;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.*;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;

public class CreateTourLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user isn't admin
        return role.equals(ADMIN);
    }
    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) throws AppException {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        //get current step in tour creation process
        int step = Integer.parseInt(request.getParameter(TOUR_CREATE_STEP));

        //get current user locale
        String userLocale = (String) session.getAttribute(LOCALE);

        //on first step(selecting city):
        if (step==1) {
            //get tour creation properties from session
            session.setAttribute(TOUR_CREATE_PROPERTIES, new TourCreationProperties());

            //get cityService from servlet context
            CityService cityService = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getCityService(context);

            //get cities in user locale
            List<City> cityList = cityService.getAllCitiesWUserLocalePreferable(userLocale);

            //add list to requestScope
            request.setAttribute(CITY_LIST, cityList);
            return;
        }

        //on second step(selecting hotel):
        if (step==2) {
            //get hotelService from servlet context
            HotelService hotelService = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getHotelService(context);

            //get city id selected on first step
            int cityId = ((TourCreationProperties) session.getAttribute(TOUR_CREATE_PROPERTIES)).getCityId();

            //get all hotels in selected city
            List<Hotel> hotelList = hotelService.getAllHotelsInCityWithUserLocalePreferable(cityId,userLocale);

            //add list to requestScope
            request.setAttribute(HOTEL_LIST, hotelList);
        }

        //third step doesn't require any data loading
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //get current page's path
        String path = request.getPathInfo();

        //get step from url
        int step = Integer.parseInt(request.getParameter(TOUR_CREATE_STEP));

        //load city selection jsp
        if (step==1) {
            return new Reroute(FORWARD, CREATE_TOUR_SELECT_CITY_JSP);
        }
        //load hotel selection jsp
        if (step==2) {
            return new Reroute(FORWARD, CREATE_TOUR_SELECT_HOTEL_JSP);
        }
        //load tour information input jsp
        if (step==3) {
            return new Reroute(FORWARD, CREATE_TOUR_SET_DESCRIPTION_JSP);
        }
        //trigger redirect to error page if step is not between 1 and 3
        return new Reroute(REDIRECT, ERROR_PAGE);
    }
}
