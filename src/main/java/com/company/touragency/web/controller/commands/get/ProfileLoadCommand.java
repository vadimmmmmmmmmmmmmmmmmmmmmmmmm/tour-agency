package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import com.razkuuuuuuu.touragency.web.controller.commands.get.base.LoadCommand;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.db.service.OrderService;
import com.razkuuuuuuu.touragency.exception.AppException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.*;
import static com.razkuuuuuuu.touragency.constants.MiscConstants.*;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.ORDERS;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.CURRENT_USER;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.ERROR_PAGE;
import static com.razkuuuuuuu.touragency.constants.Uri.SIGN_IN_PAGE;


public class ProfileLoadCommand extends LoadCommand {
    @Override
    protected boolean authorisationCheck(Integer role) {
        //proceed logic requires all roles to be able running the command
        return true;
    }

    @Override
    public void fillRequestData(HttpServletRequest request, HttpServletResponse response, Integer role) throws AppException {
        //do nothing if user is not authenticated
        if (role==0) return;
        //if current user's role is customer - load their orders
        if (role.equals(CUSTOMER)) {
            User currentUser = (User) request.getSession().getAttribute(CURRENT_USER);
            ServletContext context = request.getServletContext();
            OrderService service = ((ServiceContainer)context.getAttribute(SERVICE_CONTAINER)).getOrderService(context);
            request.setAttribute(ORDERS, service.getCustomerOrders(currentUser.getId(), currentUser.getLanguage()));
        }
    }

    @Override
    public Reroute proceed(HttpServletRequest request, HttpServletResponse response, Integer role) {
        //redirect on sign in page if no user authenticated
        if (role.equals(NO_USER)) {
            return new Reroute(REDIRECT, SIGN_IN_PAGE);
        }
        //load customer profile jsp
        if (role.equals(CUSTOMER)) {
            return new Reroute(FORWARD, CUSTOMER_PROFILE_JSP);
        }
        //load manager profile jsp
        if (role.equals(MANAGER)) {
            return new Reroute(FORWARD, MANAGER_PROFILE_JSP);
        }
        //load admin profile jsp
        if (role.equals(ADMIN)) {
            return new Reroute(FORWARD, ADMIN_PROFILE_JSP);
        }
        //redirect if role contains incorrect data
        return new Reroute(REDIRECT, ERROR_PAGE);
    }
}
