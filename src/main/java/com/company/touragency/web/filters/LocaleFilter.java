package com.razkuuuuuuu.touragency.web.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.razkuuuuuuu.touragency.web.tags.LocaleNames.*;
import static com.razkuuuuuuu.touragency.constants.SessionAttributes.LOCALE;

public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {Filter.super.init(filterConfig);}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        if (httpReq.getMethod().equalsIgnoreCase("GET")) {
            HttpSession session = httpReq.getSession();
            if (session.getAttribute(LOCALE)==null) {
                session.setAttribute(LOCALE, LOCALE_RU);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }


}
