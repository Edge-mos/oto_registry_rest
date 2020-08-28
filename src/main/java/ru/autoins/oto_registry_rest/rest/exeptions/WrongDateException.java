package ru.autoins.oto_registry_rest.rest.exeptions;

public class WrongDateException extends RuntimeException{

    public WrongDateException(String message) {
        super(message);
    }

    public WrongDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongDateException(Throwable cause) {
        super(cause);
    }
}
