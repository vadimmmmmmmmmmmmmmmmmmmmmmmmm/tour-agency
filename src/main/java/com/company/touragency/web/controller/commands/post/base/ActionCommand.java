package com.razkuuuuuuu.touragency.web.controller.commands.post.base;

import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.message.Message;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.Command;
import com.razkuuuuuuu.touragency.web.message.MessageFiller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.razkuuuuuuu.touragency.constants.SessionAttributes.CURRENT_USER;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.AUTHORISATION_ERROR;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.ERROR_PAGE;

public abstract class ActionCommand implements Command, MessageFiller {
    private static final Logger log = Logger.getLogger(Command.class);
    /**
     * check if user is eligible for performing the action
     * @param role user's role
     * @return check result
     */
    protected abstract boolean authorisationCheck(Integer role);
    /**
     * performs action using entity-related service
     *
     * @param request HttpServletRequest sent from user.
     * @param role    current user's role
     * @return success status of command body execution
     * @throws AppException exception triggered by database service calls or on user's access level check failure
     */
    public abstract boolean performAction(HttpServletRequest request, Integer role) throws AppException;
    /**
     * chooses reroute info depending on current user's role
     * in case of successful action execution
     *
     * @param request HttpServletRequest sent from user.
     * @param role    current user's role
     * @return reroute info to be processed in controller
     */
    public abstract Reroute proceedOnSuccess(HttpServletRequest request, Integer role);
    /**
     * Retrieves user role information,<br>
     * sends data to database using entity-related service,<br>
     * gets data from database afterwards (if needed)<br>
     * and selects role-dependant reroute destination of redirect type
     * @param request HttpServletRequest sent from user.
     * @param response HttpServletResponse sent to user.
     * @return reroute info to be processed in controller
     */
    public Reroute execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("starting execution");

        log.info("getting user's role");
        User currentUser = (User) request.getSession().getAttribute(CURRENT_USER);
        Integer role = currentUser==null ? 0 : currentUser.getStatus();

        if (!authorisationCheck(role)) {
            log.error("user isn't eligible for running this command");
            addMessage(request.getSession(), Message.MessageType.ERROR, AUTHORISATION_ERROR);
            return new Reroute(REDIRECT, ERROR_PAGE);
        }

        boolean isSuccessful;
        try {
            log.info("performing command-related action");
            isSuccessful = performAction(request, role);
        } catch (AppException exception) {
            log.error("error occurred while performing action -->", exception);
            return new Reroute(REDIRECT, ERROR_PAGE);
        }
        if (isSuccessful) {
            log.info("execution's successful, redirecting to success case page");
            return proceedOnSuccess(request, role);
        }
        log.error("execution was not successful, redirecting to the same page");
        StringBuilder destination = new StringBuilder(request.getRequestURI());
        String queryString = request.getQueryString();
        if (queryString!=null && !queryString.isEmpty()) {
            destination.append('?').append(queryString);
        }
        return new Reroute(REDIRECT, destination.toString());
    }

}
