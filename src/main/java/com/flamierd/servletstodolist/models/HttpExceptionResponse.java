package com.flamierd.servletstodolist.models;

public record HttpExceptionResponse(int httpCode, String message, String cause) {
}
