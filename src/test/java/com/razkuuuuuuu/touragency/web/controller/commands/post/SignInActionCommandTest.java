package com.razkuuuuuuu.touragency.web.controller.commands.post;

import com.razkuuuuuuu.touragency.db.service.ServiceContainer;
import com.razkuuuuuuu.touragency.db.service.UserService;
import com.razkuuuuuuu.touragency.entities.entity.User;
import com.razkuuuuuuu.touragency.web.reroute.Reroute;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.razkuuuuuuu.touragency.constants.ContextAttributes.*;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.EMAIL;
import static com.razkuuuuuuu.touragency.constants.RequestAttributes.PASSWORD;
import static com.razkuuuuuuu.touragency.web.reroute.TransitionType.REDIRECT;
import static com.razkuuuuuuu.touragency.constants.Uri.PROFILE_PAGE;
import static com.razkuuuuuuu.touragency.constants.Uri.SIGN_IN_PAGE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignInActionCommandTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static final String email = "email";
    private static final String password = "password";
    @InjectMocks
    private static SignInActionCommand command;
    @BeforeAll
    public static void init() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext context = mock(ServletContext.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        ServiceContainer serviceContainer = mock(ServiceContainer.class);
        UserService userService = mock(UserService.class);

        when(request.getParameter(EMAIL)).thenReturn(email);
        when(request.getParameter(PASSWORD)).thenReturn(password);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(SERVICE_CONTAINER)).thenReturn(serviceContainer);
        when(serviceContainer.getUserService(context)).thenReturn(userService);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        when(userService.getUserByEmail(email)).thenReturn(user);

        when(request.getRequestDispatcher(SIGN_IN_PAGE)).thenReturn(requestDispatcher);
    }
    @Test
    void redirectToProfileOnSuccess() {
        command = new SignInActionCommand();
        Reroute expectedReroute = new Reroute(REDIRECT, PROFILE_PAGE);
        Assertions.assertEquals(expectedReroute, command.execute(request, response));
    }
}