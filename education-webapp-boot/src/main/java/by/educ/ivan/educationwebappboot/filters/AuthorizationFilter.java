package by.educ.ivan.educationwebappboot.filters;

import by.educ.ivan.education.model.User;
import by.educ.ivan.education.service.SessionService;
import by.educ.ivan.education.service.UserService;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class AuthorizationFilter implements Filter {

    private final SessionService sessionService;

    private final UserService userService;

    public AuthorizationFilter(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if (httpRequest.getRequestURI().startsWith("/api/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String id = httpRequest.getHeader("token");
            User user = userService.getUser(Long.parseLong(id));
            sessionService.setCurrentUser(user);

            filterChain.doFilter(servletRequest, servletResponse);

            sessionService.setCurrentUser(null);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
