package com.flamierd.servletstodolist.exceptions;

public class HttpException extends RuntimeException {
    private int httpCode;

    public HttpException(int httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    public HttpException(int httpCode, String message, Throwable cause) {
        super(message, cause);
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
