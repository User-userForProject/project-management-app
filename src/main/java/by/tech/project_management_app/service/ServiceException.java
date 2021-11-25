package by.tech.project_management_app.service;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 4633062807275633145L;

    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}