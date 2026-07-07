package com.noki.noban.api.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.noki.noban.api.serializer.CustomLocalDateSerializer;

public class ExceptionResponse {
    
    private String message;
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String uri;

    public ExceptionResponse(String message, HttpStatus status, String uri) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.uri = uri.replace("uri=", "");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
