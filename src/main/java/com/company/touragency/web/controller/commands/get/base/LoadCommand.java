package com.razkuuuuuuu.touragency.web.controller.commands.get.base;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.UserService;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.message.Message;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.Command;
import com.razkuuuuuuu.touragency.web.message.MessageFiller;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.CUSTOMER;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.PathRegex.BANNED_REGEX;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.CURRENT_USER;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.AUTHORISATION_ERROR;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.BANNED_PAGE;
import static com.razkuuuuuuu.touragency.constants.Uri.ERROR_PAGE;

public abstract class LoadCommand implements Command, MessageFiller {
    private static final Logger log = Logger.getLogger(Command.class);
    /**
     * check if user is eligible for accessing the page
     * @param role user's role
     * @return check result
     */
    protected abstract boolean authorisationCheck(Integer role);
    /**
     * Fills in required request attributes data.
     * @param request HttpServletRequest sent from user.
     * @param response HttpServletResponse sent to user.
     * @param role current user's role
     * @throws AppException exception triggered by database service calls or on user's access level check failure
     */
    public abstract void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) throws AppException;
    /**
     * Chooses reroute info depending on current user's role.
     * @param request HttpServletRequest sent from user.
     * @param response HttpServletResponse sent to user.
     * @param role current user's role
     * @return reroute info to be processed in controller
     */
    public abstract Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role);
    /**
     * Retrieves user role information,<br/>
     * performs role-dependant request attributes data filling<br/>
     * and selects role-dependant reroute destination.
     * @param request HttpServletRequest sent from user.
     * @param response HttpServletResponse sent to user.
     * @return reroute info to be processed in controller
     */
    public Reroute execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("starting execution");
        try {
            log.info("getting user's role");
            Integer role = getUserStatus(request);

            if (role==-1) {
                String currentPath = request.getPathInfo();
                if(!currentPath.matches(BANNED_REGEX)) {
                    log.info("user is banned, going to banned page");
                    return new Reroute(REDIRECT, BANNED_PAGE);
                }
            }

            if (!authorisationCheck(role)) {
                log.error("user isn't eligible to perform this command");
                addMessage(request.getSession(), Message.MessageType.ERROR, AUTHORISATION_ERROR);
                return new Reroute(REDIRECT, ERROR_PAGE);
            }
            log.info("filling request data");
            fillRequestData(request, response, role);
            log.info("finishing command execution");
            return proceed(request,response, role);
        } catch (AppException e) {
            return new Reroute(REDIRECT, ERROR_PAGE);
        }
    }

    /**
     * Checks customer's current state in case of ban on every page load.
     * @param request HttpServletRequest sent from user.
     * @return user's role to be used in command
     * @throws AppException can be caused by database access error
     */
    private int getUserStatus(HttpServletRequest request) throws AppException {
        int status = 0;

        User currentUser = (User) request.getSession().getAttribute(CURRENT_USER);
        if (currentUser == null ) return status;

        status = currentUser.getStatus();
        if (status!=CUSTOMER) return status;

        ServletContext context = request.getServletContext();
        UserService service = ((ServiceContainer) context.getAttribute(SERVICE_CONTAINER)).getUserService(context);
        if (service.getUserBannedState(currentUser.getEmail())) {
            currentUser.setStatus(-1);
        }
        return currentUser.getStatus();
    }

}
