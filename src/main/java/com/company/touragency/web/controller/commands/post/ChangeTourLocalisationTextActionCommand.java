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

public class ChangeTourLocalisationTextActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an employee
        return role.equals(ADMIN);
    }

    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        String localeToBeChanged = request.getParameter(LANGUAGE);
        int tourId = Integer.parseInt(request.getParameter(TOUR_ID));
        String newTitle = request.getParameter(TOUR_NEW_TITLE);
        String newDescription = request.getParameter(TOUR_NEW_DESCRIPTION);
        if (tourId==0 || newTitle==null || newDescription==null) {
            addMessage(request.getSession(), Message.MessageType.INFO, INVALID_DATA);
            return false;
        }
        ServletContext context = request.getServletContext();
        TourService tourService = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getTourService(context);

        return tourService.updateLocale(tourId, localeToBeChanged, newTitle, newDescription);
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
