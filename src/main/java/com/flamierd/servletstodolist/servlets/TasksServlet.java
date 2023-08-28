package com.flamierd.servletstodolist.servlets;

import com.flamierd.servletstodolist.model.Task;
import com.flamierd.servletstodolist.services.TaskService;
import com.flamierd.servletstodolist.utils.JsonWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@WebServlet(name = "tasksServlet", value = "/tasks")
public class TasksServlet extends HttpServlet implements JsonWriter {
    private TaskService taskService;

    @Override
    public void init() {
        this.taskService = TaskService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String title = req.getParameter("title");
        Integer offset = req.getParameter("offset") != null ? Integer.parseInt(req.getParameter("offset")) : null;
        Integer limit = req.getParameter("limit") != null ? Integer.parseInt(req.getParameter("limit")) : null;
        List<Task> taskList = taskService.getManyTasks(title, offset, limit);

        writeJson(resp, taskList);
    }
}
