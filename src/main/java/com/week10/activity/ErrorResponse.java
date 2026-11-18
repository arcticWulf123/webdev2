package com.week10.activity;

import java.time.Instant;
import java.util.List;

public class ErrorResponse {

    private Integer status;
    private String message;
    private Instant timestamp;
    private List<FieldError> errors;

    public ErrorResponse() {
        this.timestamp = Instant.now();
    }

    public ErrorResponse(Integer status, String message, List<FieldError> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
        this.errors = errors;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }

    public static class FieldError {
        private String field;
        private String message;

        public FieldError() {
        }

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
