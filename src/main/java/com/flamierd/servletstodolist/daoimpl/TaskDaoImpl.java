package com.flamierd.servletstodolist.daoimpl;

import com.flamierd.servletstodolist.dao.TaskDao;
import com.flamierd.servletstodolist.db.ConnectionManager;
import com.flamierd.servletstodolist.dto.UpdateTaskDto;
import com.flamierd.servletstodolist.models.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TaskDaoImpl implements TaskDao {
    private static TaskDao instance;
    private final Connection connection;

    private TaskDaoImpl() {
        this.connection = ConnectionManager.open();
    }


    public static TaskDao getInstance() {
        if (instance == null) {
            instance = new TaskDaoImpl();
        }
        return instance;
    }

    @Override
    public long addTask(Task task) {
        String SQL = "INSERT INTO tasks (body, is_completed) VALUES (?, ?) RETURNING *";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, task.getBody());
            statement.setBoolean(2, task.isCompleted());

            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException("Task has not added successfully.");
        }
    }

    @Override
    public List<Task> getManyTasks(Optional<String> where, Optional<Integer> offset, Optional<Integer> limit) {
        String SQL = "SELECT * FROM tasks"
                     + where.map(s -> " WHERE " + s).orElse("")
                     + offset.map(integer -> " OFFSET " + integer).orElse("")
                     + limit.map(integer -> " LIMIT " + integer).orElse("") + ";";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            ResultSet rs = statement.executeQuery();
            return processResultSet(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Invalid arguments to execute query.");
        }
    }

    @Override
    public Optional<Task> getFirstTask(long id) {
        String SQL = "SELECT * FROM tasks WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, id);
            return Optional.of(processResultSet(statement.executeQuery()).get(0));
        } catch (SQLException | IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<Task> updateTask(long id, UpdateTaskDto taskDto) {
        Optional<Task> optionalTask = this.getFirstTask(id);
        String SQL = "UPDATE tasks SET body = ?, is_completed = ? WHERE id = ? RETURNING *;";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            Task task = optionalTask.get();
            statement.setString(1, Optional.ofNullable(taskDto.body()).orElse(task.getBody()));
            statement.setBoolean(2, Optional.ofNullable(taskDto.completed()).orElse(task.isCompleted()));
            statement.setLong(3, id);
            return Optional.of(processResultSet(statement.executeQuery()).get(0));
        } catch (SQLException | IndexOutOfBoundsException | NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Task> deleteTask(long id) {
        String SQL = "DELETE FROM tasks WHERE id = ? RETURNING *";

        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setLong(1, id);
            return Optional.of(processResultSet(statement.executeQuery()).get(0));
        } catch (SQLException | IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    private List<Task> processResultSet(ResultSet resultSet) throws SQLException {
        List<Task> result = new ArrayList<>();

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String body = resultSet.getString("body");
            boolean completed = resultSet.getBoolean("is_completed");

            result.add(new Task(id, body, completed));

        }

        return result;
    }
}
