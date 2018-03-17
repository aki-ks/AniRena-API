package de.kaysubs.tracker.anirena.exception;

public class AnirenaException extends RuntimeException {

    public AnirenaException() {}

    public AnirenaException(String message) {
        super(message);
    }

    public AnirenaException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnirenaException(Throwable cause) {
        super(cause);
    }

    public AnirenaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
