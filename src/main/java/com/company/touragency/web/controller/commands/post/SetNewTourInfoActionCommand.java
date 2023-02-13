package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.entities.bean.TourCreationProperties;
import com.razkuuuuuuu.touragency.entities.entity.TourLocalisationInfo;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.MESSAGES;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.TOUR_CREATE_PROPERTIES;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.CATALOG_PAGE;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.*;
import static com.razkuuuuuuu.touragency.web.tags.LocaleNames.*;

public class SetNewTourInfoActionCommand extends ActionCommand {
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
        List<String> supportedLanguages = Arrays.asList(request.getParameterValues(SUPPORTED_LANGUAGE));
        if (supportedLanguages.size()==0) {
            List<String> errorList = new ArrayList<>();
            errorList.add(NO_LANGUAGES_SELECTED);
            request.getSession().setAttribute(MESSAGES, errorList);
            return false;
        }
        List<TourLocalisationInfo> localisationInfo = new ArrayList<>();
        if (supportedLanguages.contains(LOCALE_EN)) {
            TourLocalisationInfo info = new TourLocalisationInfo();
            info.setLocale(LOCALE_EN);
            info.setTitle(request.getParameter(TOUR_TITLE_EN));
            info.setDescription(request.getParameter(TOUR_DESCRIPTION_EN));
            localisationInfo.add(info);
            if(info.getTitle() == null || info.getDescription() == null) {
                List<String> errorList = new ArrayList<>();
                errorList.add(LOCALISATION_FIELD_EMPTY);
                request.getSession().setAttribute(MESSAGES, errorList);
                return false;
            }
        }
        if (supportedLanguages.contains(LOCALE_UA)) {
            TourLocalisationInfo info = new TourLocalisationInfo();
            info.setLocale(LOCALE_UA);
            info.setTitle(request.getParameter(TOUR_TITLE_UA));
            info.setDescription(request.getParameter(TOUR_DESCRIPTION_UA));
            localisationInfo.add(info);
            if(info.getTitle() == null || info.getDescription() == null) {
                List<String> errorList = new ArrayList<>();
                errorList.add(LOCALISATION_FIELD_EMPTY);
                request.getSession().setAttribute(MESSAGES, errorList);
                return false;
            }
        }
        if (supportedLanguages.contains(LOCALE_RU)) {
            TourLocalisationInfo info = new TourLocalisationInfo();
            info.setLocale(LOCALE_RU);
            info.setTitle(request.getParameter(TOUR_TITLE_RU));
            info.setDescription(request.getParameter(TOUR_DESCRIPTION_RU));
            localisationInfo.add(info);
            if(info.getTitle() == null || info.getDescription() == null) {
                List<String> errorList = new ArrayList<>();
                errorList.add(LOCALISATION_FIELD_EMPTY);
                request.getSession().setAttribute(MESSAGES, errorList);
                return false;
            }
        }
        properties.setLocalisationList(localisationInfo);

        properties.setDepartureTakeoffDate(request.getParameter(TOUR_DEPARTURE_TAKEOFF_DATE));
        properties.setDepartureTakeoffTime(request.getParameter(TOUR_DEPARTURE_TAKEOFF_TIME));
        properties.setReturnTakeoffDate(request.getParameter(TOUR_RETURN_TAKEOFF_DATE));
        properties.setReturnTakeoffTime(request.getParameter(TOUR_RETURN_TAKEOFF_TIME));

        properties.setDiscountAmount(Integer.parseInt(request.getParameter(TOUR_DISCOUNT_AMOUNT)));
        properties.setDiscountPer(Integer.parseInt(request.getParameter(TOUR_DISCOUNT_PER)));
        properties.setDiscountMax(Integer.parseInt(request.getParameter(TOUR_DISCOUNT_MAX)));

        properties.setTicketCount(Integer.parseInt(request.getParameter(TOUR_TICKET_COUNT)));
        properties.setTicketPrice(BigDecimal.valueOf(Double.parseDouble(request.getParameter(TOUR_TICKET_PRICE))));
        properties.setTourType(request.getParameter(TOUR_TYPE));

        TourService tourService = container.getTourService(context);
        if (tourService.addNewTour(properties)) {
            request.getSession().setAttribute(TOUR_CREATE_PROPERTIES, null);
            return true;
        } else {
            List<String> errorList = new ArrayList<>();
            errorList.add(TOUR_ADD_FAILURE);
            request.getSession().setAttribute(MESSAGES, errorList);
            return false;
        }
    }



    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, CATALOG_PAGE);
    }
}
