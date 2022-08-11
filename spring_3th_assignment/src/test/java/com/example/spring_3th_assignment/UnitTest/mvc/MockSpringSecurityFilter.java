package com.example.spring_3th_assignment.UnitTest.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MockSpringSecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.getContext()
                .setAuthentication(
                        (Authentication) ((HttpServletRequest) request ).getUserPrincipal());

        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        SecurityContextHolder.clearContext();
    }
}
