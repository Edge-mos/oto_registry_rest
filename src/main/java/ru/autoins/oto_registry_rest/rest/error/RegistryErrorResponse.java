package ru.autoins.oto_registry_rest.rest.error;

import java.time.LocalDateTime;

public class RegistryErrorResponse {
    private int status;
    private String message;
    private LocalDateTime errTime;

    public RegistryErrorResponse() {
    }

    public RegistryErrorResponse(int status, String message, LocalDateTime errTime) {
        this.status = status;
        this.message = message;
        this.errTime = errTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getErrTime() {
        return errTime;
    }

    public void setErrTime(LocalDateTime errTime) {
        this.errTime = errTime;
    }
}
