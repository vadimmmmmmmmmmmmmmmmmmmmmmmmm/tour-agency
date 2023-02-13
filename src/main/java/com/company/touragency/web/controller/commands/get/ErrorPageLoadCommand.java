package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.ERROR_JSP;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;

public class ErrorPageLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //not required
        return true;
    }
    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //not required
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //forward to error page
        return new Reroute(FORWARD, ERROR_JSP);
    }
}
