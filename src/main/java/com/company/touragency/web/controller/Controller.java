package com.razkuuuuuuu.touragency.web.controller;

import com.razkuuuuuuu.touragency.web.controller.commands.Command;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommandContainer;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommandName;
import com.razkuuuuuuu.touragency.web.controller.commands.post.base.ActionCommandContainer;
import com.razkuuuuuuu.touragency.web.message.Message;
import com.razkuuuuuuu.touragency.web.message.MessageFiller;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import static com.razkuuuuuuu.touragency.constants.RequestAttributes.COMMAND;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.MESSAGES;
import static com.razkuuuuuuu.touragency.web.message.MessageNames.PAGE_NOT_FOUND;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.CATALOG_PAGE;
import static com.razkuuuuuuu.touragency.constants.Uri.ERROR_PAGE;

@WebServlet (name="controller", urlPatterns = {"/tour-agency/*", "/tour-agency"})
public class Controller extends HttpServlet implements Serializable, MessageFiller {
    private static final long serialVersionUID = 7674471731459748077L;
    private LoadCommandContainer loadCommandContainer;
    private ActionCommandContainer actionCommandContainer;
    /**
     * Initiate Command containers
     * @throws ServletException general exception thrown by servlet.
     */
    @Override
    public void init() throws ServletException {
        actionCommandContainer = new ActionCommandContainer();
        loadCommandContainer = new LoadCommandContainer();
        super.init();
    }
    /**
     * Processes HTTP POST request. <br/>
     * Executes database service-related action.
     * @param request HttpServletRequest sent from user.
     * @param response HttpServletResponse sent to user.
     * @throws ServletException general exception thrown by servlet.
     * @throws IOException exception thrown by response PrintWriter.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);

        if (!actionCommandContainer.hasCommand(commandName)) {
            doReroute(new Reroute(REDIRECT, ERROR_PAGE), request, response);
            return;
        }

        Command command = actionCommandContainer.getCommand(commandName);
        Reroute reroute = command.execute(request,response);

        //Post request commands should not forward after execution
        if (reroute.getType().equals(FORWARD)) {
            reroute = new Reroute(REDIRECT, ERROR_PAGE);
        }
        doReroute(reroute, request, response);
    }
    /**
     * Processes HTTP GET request.<br/>
     * Loads page with required request attributes.
     * @param request HttpServletRequest sent from user.
     * @param response HttpServletResponse sent to user.
     * @throws ServletException general exception thrown by servlet.
     * @throws IOException exception thrown by response PrintWriter.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        //redirect to catalog if in directory root
        if (Objects.equals(pathInfo, "/")) {
            doReroute(new Reroute(REDIRECT, CATALOG_PAGE), request, response);
            return;
        }

        //get command name paired with current page's address url
        LoadCommandName commandName = loadCommandContainer.getLoadCommandNameByRegex(pathInfo);

        //if no command name found - redirect to error page
        if (commandName == null) {
            addMessage(request.getSession(), Message.MessageType.ERROR, PAGE_NOT_FOUND);
            doReroute(new Reroute(REDIRECT, ERROR_PAGE), request, response);
            return;
        }

        //get command from container
        Command command = loadCommandContainer.getCommand(commandName);

        //Get reroute info from command
        Reroute reroute = command.execute(request,response);

        //reroute after command has been executed
        doReroute(reroute, request, response);

        // remove all errors once they've been shown (after forward on jsp page)
        if (reroute.getType().equals(FORWARD)) {
            request.getSession().setAttribute(MESSAGES, null);
        }
    }
    /**
     * Performs forward or redirect depending on reroute object returned from command.
     * @param reroute contains type of reroute and destination (page uri or jsp file address).
     * @param request HttpServletRequest sent from user.
     * @param response HttpServletResponse sent to user.
     * @throws ServletException general exception thrown by servlet.
     * @throws IOException exception thrown by response PrintWriter.
     */
    private void doReroute(Reroute reroute, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (reroute.getType().equals(FORWARD)) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(reroute.getDestination());
            dispatcher.forward(request, response);
            return;
        }
        response.sendRedirect(reroute.getDestination());
    }
}