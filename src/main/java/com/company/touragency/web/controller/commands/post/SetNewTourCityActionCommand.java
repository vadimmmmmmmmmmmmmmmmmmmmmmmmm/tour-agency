package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.CityService;
import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.entities.bean.TourCreationProperties;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.message.MessageNames;
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

public class SetNewTourCityActionCommand extends ActionCommand {
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
        int citySelection = Integer.parseInt(request.getParameter(CITY_SELECTION));
        if(citySelection==1) {
            properties.setCityId(Integer.parseInt(request.getParameter(CITY)));
        } else {
            CityService cityService = container.getCityService(context);
            String englishName = request.getParameter(CITY_TITLE_EN);
            String ukrainianName = request.getParameter(CITY_TITLE_UA);
            String russianName = request.getParameter(CITY_TITLE_RU);
            int cityId = cityService.addCity();
            if (cityId!=0) {
                cityService.setCityLocalisation(cityId, englishName, ukrainianName, russianName);
                properties.setCityId(cityId);
            } else {
                throw new AppException(MessageNames.INVALID_DATA);
            }
        }
        request.getSession().setAttribute(TOUR_CREATE_PROPERTIES, properties);
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, CREATE_TOUR_PAGE+"?step="+2);
    }
}
