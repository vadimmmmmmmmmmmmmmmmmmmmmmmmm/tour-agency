package com.razkuuuuuuu.touragency.web.controller.commands.get;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.UserService;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.SERVICE_CONTAINER;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.CURRENT_USER;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.FORWARD;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.PROFILE_PAGE;
import static com.razkuuuuuuu.touragency.web.controller.commands.get.base.JspFiles.SIGN_IN_JSP;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignInLoadCommandTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static SignInLoadCommand command;
    private static HttpSession session;

    @BeforeAll
    public static void init() {
        command = new SignInLoadCommand();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        ServletContext context = mock(ServletContext.class);
        ServiceContainer serviceContainer = mock(ServiceContainer.class);
        UserService userService = mock(UserService.class);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(SERVICE_CONTAINER)).thenReturn(serviceContainer);
        when(serviceContainer.getUserService(context)).thenReturn(userService);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void forwardToSignInPageOnNoUserInSession() {
        when(session.getAttribute(CURRENT_USER)).thenReturn(null);
        Reroute expected = new Reroute(FORWARD, SIGN_IN_JSP);
        Assertions.assertEquals(expected, command.execute(request,response));
    }

    @Test
    void redirectToProfileOnUserActive() {
        User user = new User();
        user.setStatus(1);
        when(session.getAttribute(CURRENT_USER)).thenReturn(user);

        Reroute expected = new Reroute(REDIRECT, PROFILE_PAGE);
        Assertions.assertEquals(expected, command.execute(request,response));
    }
}