package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.ADD_EMPLOYEE_JSP;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.ADMIN;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;

public class AddEmployeeLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //trigger redirect to error page if user isn't admin
        return role.equals(ADMIN);
    }

    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //not required
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //forward to adding employee page
        return new Reroute(FORWARD, ADD_EMPLOYEE_JSP);
    }
}
