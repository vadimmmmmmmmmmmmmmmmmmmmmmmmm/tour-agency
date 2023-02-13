package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.exception.AppException;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.razkuuuuuuu.touragency.constants.MiscConstants.BANNED;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.BANNED_JSP;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;

public class BannedPageLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user isn't banned
        return role.equals(BANNED);
    }

    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //not required
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //forward to banned page
        return new Reroute(FORWARD, BANNED_JSP);
    }
}
