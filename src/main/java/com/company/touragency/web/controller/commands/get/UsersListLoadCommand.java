package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.db.service.UserService;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.USERS_JSP;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.USER_LIST;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;

public class UsersListLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user isn't admin
        return role.equals(ADMIN);
    }

    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) throws AppException {
        //get UserService from ServletContext
        ServletContext context = request.getServletContext();
        UserService service = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getUserService(context);

        //get user list
        List<User> users = service.getAll();
        //put it into RequestScope
        request.setAttribute(USER_LIST, users);
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //load users jsp
        return new Reroute(FORWARD, USERS_JSP);
    }
}
