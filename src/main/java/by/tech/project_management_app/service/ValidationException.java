package by.tech.project_management_app.service;

public class ValidationException extends Exception {

    private static final long serialVersionUID = -2048434239150807161L;

    public ValidationException() {

    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}