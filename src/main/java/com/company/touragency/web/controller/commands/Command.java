package com.razkuuuuuuu.touragency.web.controller.commands;

import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface Command {
    /**
     * execute command
     * @param request HttpServletRequest sent from user.
     * @param response HttpServletResponse sent to user.
     * @return reroute info to be processed in controller.
     */
    Reroute execute(HttpServletRequest request, HttpServletResponse response);
}
