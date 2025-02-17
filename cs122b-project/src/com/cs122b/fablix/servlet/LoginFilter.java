package com.cs122b.fablix.servlet;



import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs122b.fablix.common.Constants;

import java.io.*;
import java.io.IOException;

/**
 * Servlet Filter implementation class: LoginFilter.
 * All URL patterns will go through the LoginFilter
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        System.out.println("LoginFilter: " + httpRequest.getRequestURI());

        
        // Check if the URL is allowed to be accessed without log in
        if (this.isUrlAllowedWithoutLogin(httpRequest.getRequestURI())) {
            // Keep default action: pass along the filter chain
            chain.doFilter(request, response);
            return;
        }

        // Redirect to login page if the "user" attribute doesn't exist in session
        if (httpRequest.getSession().getAttribute(Constants.CURRENT_CUSTOMER) == null && httpRequest.getSession().getAttribute(Constants.CURRENT_EMPLOYEE) == null) {
        	System.out.println("customer:"+ httpRequest.getSession().getAttribute(Constants.CURRENT_CUSTOMER));
        	System.out.println("admin:"+ httpRequest.getSession().getAttribute(Constants.CURRENT_EMPLOYEE));
            httpResponse.sendRedirect("/cs122b-project/page/login.html");
        } else {
            // If the user exists in current session, redirects the user to the corresponding URL
            chain.doFilter(request, response);
        }
        
    }

    // Setup your own rules here to allow accessing some resources without logged in
    // Always allow your own login related requests (html, js, servlet, etc..)
    // You might also want to allow some CSS files, etc..
    private boolean isUrlAllowedWithoutLogin(String requestURI) {

        return requestURI.endsWith("page/login.html") || requestURI.endsWith("js/login.js")
                || requestURI.endsWith("api/login") || requestURI.endsWith("css/login.css")
                
                || requestURI.endsWith("page/adminLogin.html") || requestURI.endsWith("js/adminLogin.js")
                || requestURI.endsWith("api/adminLogin");
                
    }

    /**
     * This class implements the interface: Filter. In Java, a class that implements an interface
     * must implemented all the methods declared in the interface. Therefore, we include the methods
    * below.
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) {
    }

    public void destroy() {
    }
}