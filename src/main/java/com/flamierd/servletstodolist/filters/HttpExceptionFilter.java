package com.flamierd.servletstodolist.filters;

import com.flamierd.servletstodolist.exceptions.HttpException;
import com.flamierd.servletstodolist.utils.JsonWriter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpFilter;

import java.io.IOException;


@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name = "errorHandlerUrl", value = "/error-handler")})
public class HttpExceptionFilter extends HttpFilter implements JsonWriter {
    private String errorHandlerUrl;

    @Override
    public void init(FilterConfig config) {
        this.errorHandlerUrl = config.getInitParameter("errorHandlerUrl");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            super.doFilter(req, res, chain);
        } catch (HttpException e) {
            if (e.getCause() != null) {
                req.setAttribute("error_cause", e.getCause().getMessage());
            }
            req.setAttribute("error_message", e.getMessage());
            req.setAttribute("error_http_code", e.getHttpCode());

            req.getRequestDispatcher(errorHandlerUrl).forward(req, res);
        } catch (Exception e) {
            if (e.getCause() != null) {
                req.setAttribute("error_cause", e.getCause().getMessage());
            }
            req.setAttribute("error_message", e.getMessage());

            req.getRequestDispatcher(errorHandlerUrl).forward(req, res);
        }
    }
}
