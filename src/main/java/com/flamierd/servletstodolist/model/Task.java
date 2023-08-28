package com.flamierd.servletstodolist.model;

public class Task {
    private long id;
    private String body;
    private boolean completed;

    public Task(String body, boolean completed) {
        this.body = body;
        this.completed = completed;
    }

    public Task(long id, String body, boolean completed) {
        this.id = id;
        this.body = body;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", body='" + body + '\'' +
               ", completed=" + completed +
               '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
