package ru.knitu.app.service.authorize;

public class UnableToAuthorizeException extends RuntimeException {
    public UnableToAuthorizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToAuthorizeException(Throwable cause) {
        super(cause);
    }
}
