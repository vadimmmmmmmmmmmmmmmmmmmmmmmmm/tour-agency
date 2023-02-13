package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.db.service.UserService;
import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.message.Message;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.NO_USER;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.EMAIL;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.PASSWORD;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.*;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.PROFILE_PAGE;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.*;

public class SignInActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user already authorised in session
        return role.equals(NO_USER);
    }

    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        ServletContext context = request.getServletContext();
        UserService userService = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getUserService(context);
        String email = request.getParameter(EMAIL);
        User user = userService.getUserByEmail(email);

        if (user==null) {
            addMessage(request.getSession(), Message.MessageType.ERROR, USER_NOT_FOUND);
            return false;
        }

        if (!Objects.equals(user.getPassword(), request.getParameter(PASSWORD))) {
            addMessage(request.getSession(), Message.MessageType.ERROR, INVALID_PASSWORD);
            return false;
        }

        request.getSession().setAttribute(CURRENT_USER, user);
        request.getSession().setAttribute(LOCALE, user.getLanguage());
        addMessage(request.getSession(), Message.MessageType.INFO, AUTHENTICATION_SUCCESS);
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, PROFILE_PAGE);
    }
}
