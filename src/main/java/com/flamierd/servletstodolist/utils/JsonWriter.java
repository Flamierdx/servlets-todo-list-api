package com.flamierd.servletstodolist.utils;

import com.google.gson.Gson;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public interface JsonWriter {
    Gson gson = new Gson();
    default <T> void writeJson(ServletResponse resp, T data) {
        Gson gson = new Gson();
        resp.setContentType("application/json");
        try (PrintWriter writer = resp.getWriter()) {
            writer.print(gson.toJson(data));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
