package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.SIGN_IN_JSP;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.NO_USER;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.PROFILE_PAGE;

public class SignInLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //proceed logic requires all roles to be able running the command
        return true;
    }

    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //not required
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //if user already authenticated - redirect to profile page
        if (!role.equals(NO_USER)) {
            return new Reroute(REDIRECT, PROFILE_PAGE);
        }
        //load sign in jsp otherwise
        return new Reroute(FORWARD, SIGN_IN_JSP);
    }
}
