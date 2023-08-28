package com.flamierd.servletstodolist.services;

import com.flamierd.servletstodolist.dao.TaskDao;
import com.flamierd.servletstodolist.daoimpl.TaskDaoImpl;
import com.flamierd.servletstodolist.dto.CreateTaskDto;
import com.flamierd.servletstodolist.dto.UpdateTaskDto;
import com.flamierd.servletstodolist.exceptions.HttpException;
import com.flamierd.servletstodolist.model.Task;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class TaskService {
    private static TaskService instance;
    private final TaskDao taskDao;

    private TaskService() {
        this.taskDao = TaskDaoImpl.getInstance();
    }

    public static TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }


    public Task createTask(CreateTaskDto dto) {
        try {
            Task task = new Task(dto.body(), false);
            task.setId(this.taskDao.addTask(task));
            return task;
        } catch (RuntimeException e) {
            throw new HttpException(HttpServletResponse.SC_BAD_REQUEST, "Wrong request for this breakpoint", e);
        }
    }

    public Task getFirstTask(long id) {
        Optional<Task> task = this.taskDao.getFirstTask(id);
        return task.orElseThrow(() -> new HttpException(HttpServletResponse.SC_BAD_REQUEST, "Task has not found."));
    }

    public List<Task> getManyTasks(String searchByTitle, Integer offset, Integer limit) {
        String where = null;
        if (searchByTitle != null) {
            where = "body ILIKE '%" + searchByTitle + "%'";
        }

        try {
            return this.taskDao.getManyTasks(
                    Optional.ofNullable(where),
                    Optional.ofNullable(offset),
                    Optional.ofNullable(limit)
            );
        } catch (RuntimeException e) {
            throw new HttpException(HttpServletResponse.SC_BAD_REQUEST, "Wrong request for this breakpoint.", e);
        }
    }

    public Task updateTask(long id, UpdateTaskDto dto) {
        Optional<Task> task = this.taskDao.updateTask(id, dto);
        return task.orElseThrow(() -> new HttpException(HttpServletResponse.SC_BAD_REQUEST, "Task update has been failed."));
    }

    public Task deleteTask(long id) {
        Optional<Task> task = this.taskDao.deleteTask(id);
        return task.orElseThrow(() -> new HttpException(HttpServletResponse.SC_BAD_REQUEST, "Task delete has been failed."));
    }
}
