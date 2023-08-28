package com.flamierd.servletstodolist.servlets;

import com.flamierd.servletstodolist.model.HttpExceptionResponse;
import com.flamierd.servletstodolist.utils.JsonWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "errorHandlerServlet", value = "/error-handler")
public class ErrorHandlerServlet extends HttpServlet implements JsonWriter {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        String errorMessage = (String) req.getAttribute("error_message");
        int errorHttpCode = (int) Optional.ofNullable(req.getAttribute("error_http_code"))
                .orElse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String errorCause = (String) req.getAttribute("error_cause");

        if (res instanceof HttpServletResponse httpRes) {
            httpRes.setStatus(errorHttpCode);
        }
        writeJson(res, new HttpExceptionResponse(errorHttpCode, errorMessage, errorCause));
    }
}
