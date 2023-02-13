package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.http.HttpServletRequest;

import static com.razkuuuuuuu.touragency.constants.MiscConstants.NO_USER;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.LOCALE;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;

public class ChangeLocaleActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        /*
        user's locale isn't designed to be changed,
        so they will see tours in their locale only,
        and multiple locales for user aren't implemented
        so this command must trigger redirect to error page
        if there is authenticated user
         */
        return role.equals(NO_USER);
    }
    @Override
    public boolean performAction(HttpServletRequest request, Integer role) {
        //update locale in session
        request.getSession().setAttribute(LOCALE, request.getParameter(LOCALE));
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        //redirect to the same page
        StringBuilder destination = new StringBuilder(request.getRequestURI());
        String queryString = request.getQueryString();
        if (queryString!=null && !queryString.isEmpty()) {
            destination.append('?').append(queryString);
        }
        return new Reroute(REDIRECT, destination.toString());
    }
}
