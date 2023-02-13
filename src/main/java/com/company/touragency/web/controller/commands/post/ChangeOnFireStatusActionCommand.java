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
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.ON_FIRE;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.TOUR_ID;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.CATALOG_PAGE;

public class ChangeOnFireStatusActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an employee
        return role>CUSTOMER;
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        ServletContext context = request.getServletContext();
        TourService service = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getTourService(context);
        int tourId = Integer.parseInt(request.getParameter(TOUR_ID));
        boolean currentStatus = Boolean.parseBoolean(request.getParameter(ON_FIRE));
        service.changeOnFireStatus(tourId, !currentStatus);
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, CATALOG_PAGE);
    }
}
