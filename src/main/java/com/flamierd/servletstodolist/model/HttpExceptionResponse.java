package com.flamierd.servletstodolist.model;

public record HttpExceptionResponse(int httpCode, String message, String cause) {
}
