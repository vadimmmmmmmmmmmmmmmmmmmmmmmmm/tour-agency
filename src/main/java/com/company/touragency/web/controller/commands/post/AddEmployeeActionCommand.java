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

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.MANAGER;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.*;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.LOCALE;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.*;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.USERS_PAGE;

public class AddEmployeeActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user is not an admin
        return role.equals(ADMIN);
    }

    @Override
    public boolean performAction(HttpServletRequest request, Integer role) throws AppException {
        //get UserService from ServletContext
        ServletContext context = request.getServletContext();
        UserService service = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getUserService(context);

        //set manager status and insert other data from jsp form
        User user = new User();
        user.setStatus(MANAGER);
        user.setEmail(request.getParameter(EMAIL));
        user.setPassword(request.getParameter(PASSWORD));
        user.setName(request.getParameter(NAME));
        user.setSurname(request.getParameter(SURNAME));
        user.setLanguage(request.getParameter(LOCALE));

        //check if manager was added
        boolean success = service.addToDB(user);
        if (!success) {
            //add message to show to user
            addMessage(request.getSession(), Message.MessageType.ERROR, EMPLOYEE_NOT_ADDED);
            //returning false causes command to return redirect to the same page it was triggered on
            return false;
        }
        //add info message
        addMessage(request.getSession(), Message.MessageType.INFO, EMPLOYEE_ADDED);
        //go to proceedOnSuccess method
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        //redirect to users page
        return new Reroute(REDIRECT, USERS_PAGE);
    }
}
