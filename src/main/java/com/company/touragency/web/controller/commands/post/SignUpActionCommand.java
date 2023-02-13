package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.db.service.UserService;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.message.Message;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.CUSTOMER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.NO_USER;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.*;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.*;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.*;

public class SignUpActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user already authorised in session
        return role.equals(NO_USER);
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        //get UserService from ServletContext
        ServletContext context = request.getServletContext();
        UserService userService = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getUserService(context);

        //get email from jsp form
        String email = request.getParameter(EMAIL);

        //try getting user from database
        User user = userService.getUserByEmail(email);

        //if user exists - trigger error
        if (user!=null ) {
            addMessage(request.getSession(), Message.MessageType.ERROR, USER_ALREADY_EXISTS);
            throw new AppException(USER_ALREADY_EXISTS);
        }

        user = new User();
        user.setStatus(CUSTOMER);
        user.setEmail(request.getParameter(EMAIL));
        user.setPassword(request.getParameter(PASSWORD));
        user.setName(request.getParameter(NAME));
        user.setSurname(request.getParameter(SURNAME));
        user.setLanguage((String) request.getSession().getAttribute(LOCALE));

        boolean success = userService.addToDB(user);
        if (!success) {
            addMessage(request.getSession(), Message.MessageType.ERROR, USER_NOT_CREATED);
            return false;
        }
        request.getSession().setAttribute(CURRENT_USER, userService.getUserByEmail(email));
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, PROFILE_PAGE);
    }
}
