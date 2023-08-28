package com.flamierd.servletstodolist.dao;

import com.flamierd.servletstodolist.dto.UpdateTaskDto;
import com.flamierd.servletstodolist.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao {
    long addTask(Task task);

    List<Task> getManyTasks(Optional<String> where, Optional<Integer> offset, Optional<Integer> limit);

    Optional<Task> getFirstTask(long id);


    Optional<Task> updateTask(long id, UpdateTaskDto taskDto);


    Optional<Task> deleteTask(long id);

}
