package com.flamierd.servletstodolist.servlets;

import com.flamierd.servletstodolist.dto.CreateTaskDto;
import com.flamierd.servletstodolist.dto.UpdateTaskDto;
import com.flamierd.servletstodolist.model.Task;
import com.flamierd.servletstodolist.services.TaskService;
import com.flamierd.servletstodolist.utils.JsonWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "taskByIdServlet", value = "/task")
public class TaskByIdServlet extends HttpServlet implements JsonWriter {
    private TaskService taskService;


    @Override
    public void init() {
        this.taskService = TaskService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CreateTaskDto dto = gson.fromJson(req.getReader(), CreateTaskDto.class);
        Task task = taskService.createTask(dto);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        writeJson(resp, task);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        Task task = taskService.getFirstTask(id);

        writeJson(resp, task);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        long id = Long.parseLong(req.getParameter("id"));
        Task task = taskService.deleteTask(id);

        writeJson(resp, task);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        UpdateTaskDto dto = gson.fromJson(req.getReader(), UpdateTaskDto.class);
        Task task = taskService.updateTask(id, dto);

        writeJson(resp, task);
    }
}
