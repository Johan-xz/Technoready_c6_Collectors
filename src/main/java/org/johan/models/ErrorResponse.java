package org.johan.models;

// Esta clase estándar asegura que todos tus errores se vean igual (C2)
public class ErrorResponse {
    private String status;
    private String message;

    public ErrorResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters para que GSON pueda serializarlo
    public String getStatus() { return status; }
    public String getMessage() { return message; }
}