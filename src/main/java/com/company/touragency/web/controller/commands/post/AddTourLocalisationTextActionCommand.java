package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.TourService;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.message.Message;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.INVALID_DATA;

public class AddTourLocalisationTextActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an admin
        return role.equals(ADMIN);
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        //get new locale name with title and description in it from jsp form
        String newLocale = request.getParameter(LANGUAGE);
        String newTitle = request.getParameter(TOUR_NEW_TITLE);
        String newDescription = request.getParameter(TOUR_NEW_DESCRIPTION);

        //get tour id from jsp form
        int tourId = Integer.parseInt(request.getParameter(TOUR_ID));

        //incorrect data check
        if (tourId==0 || newTitle==null || newDescription==null) {
            addMessage(request.getSession(), Message.MessageType.ERROR, INVALID_DATA);
            //returning false causes command to return redirect to the same page it was triggered on
            return false;
        }

        //get TourService from ServletContext
        ServletContext context = request.getServletContext();
        TourService tourService = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getTourService(context);

        //add language to tour supported
        boolean success = tourService.addLocale(tourId, newLocale);

        //add tour localisation title and description
        success &= tourService.addLocaleText(tourId, newLocale, newTitle, newDescription);

        return success;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        //this command must return to the same page in both cases of actionPerform return value - to the tour info page
        StringBuilder destination = new StringBuilder(request.getRequestURI());
        String queryString = request.getQueryString();
        if (queryString!=null && !queryString.isEmpty()) {
            destination.append('?').append(queryString);
        }
        return new Reroute(REDIRECT, destination.toString());
    }
}
