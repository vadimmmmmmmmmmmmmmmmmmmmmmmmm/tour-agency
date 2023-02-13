package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommand;
import jakarta.servlet.http.HttpServletRequest;

import static com.razkuuuuuuu.touragency.constants.MiscConstants.NO_USER;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.*;

public class SignOutActionCommand extends ActionCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //can't sign out if not signed in
        return !role.equals(NO_USER);
    }

    @Override
    public boolean performAction(HttpServletRequest request, Integer role) {
        request.getSession().invalidate();
        return true;
    }

    @Override
    public Reroute proceedOnSuccess(HttpServletRequest request, Integer role) {
        return new Reroute(REDIRECT, CATALOG_PAGE);
    }
}
