package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.entities.entity.TourLocalisationInfo;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import com.razkuuuuuuu.touragency.entities.entity.Tour;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.TOUR_SUPPORTED_LANGUAGES;
import static com.razkuuuuuuu.touragency.constants.Uri.ERROR_PAGE;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.TOUR_NOT_FOUND;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.*;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.*;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.TOUR;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.LOCALE;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;
import static com.razkuuuuuuu.touragency.web.message.Message.MessageType.ERROR;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;

public class TourInfoLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //not required
        return true;
    }

    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) throws AppException {
        //get tour id from url
        int id = Integer.parseInt(request.getParameter("id"));

        //get TourService from context
        ServletContext context = request.getServletContext();
        TourService service = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getTourService(context);
        // if role is less than manager, get tour in user locale
        if (role<MANAGER) {
            Tour tour = service.getByIdInUserLocale(id, (String) request.getSession().getAttribute(LOCALE));
            // if tour with entered id doesn't exist, or it doesn't have support for user's language
            if (tour==null) {
                // add no tour found error to messages
                addMessage(request.getSession(), ERROR, TOUR_NOT_FOUND);
                // trigger redirect to error page
                throw new AppException(TOUR_NOT_FOUND);
            }
            //put tour into requestScope
            request.setAttribute(TOUR, tour);
        //else show all supported locales for tour
        } else {
            Tour tour = service.getByIdExtended(id);
            // if tour with entered id doesn't exist
            if (tour==null) {
                // add no tour found error to messages
                addMessage(request.getSession(), ERROR, TOUR_NOT_FOUND);
                // trigger redirect to error page
                throw new AppException(TOUR_NOT_FOUND);
            }
            //get localisation info on tour
            Map<String, TourLocalisationInfo> localisationInfoMap = service.getAllLocales(id);
            //put data in requestScope
            request.setAttribute(TOUR, tour);
            request.setAttribute(TOUR_SUPPORTED_LANGUAGES, localisationInfoMap);
        }
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //load no user tour info jsp
        if (role.equals(NO_USER)) {
            return new Reroute(FORWARD, NO_USER_TOUR_INFO);
        }
        //load customer tour info jsp
        if (role.equals(CUSTOMER)) {
            return new Reroute(FORWARD, CUSTOMER_TOUR_INFO_JSP);
        }
        //load manager tour info jsp
        if (role.equals(MANAGER)) {
            return new Reroute(FORWARD, MANAGER_TOUR_INFO_JSP);
        }
        //load admin tour info jsp
        if (role.equals(ADMIN)) {
            return new Reroute(FORWARD, ADMIN_TOUR_INFO_JSP);
        }
        return new Reroute(REDIRECT, ERROR_PAGE);
    }
}
