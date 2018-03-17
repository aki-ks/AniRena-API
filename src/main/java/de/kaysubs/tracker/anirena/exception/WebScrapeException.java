package de.kaysubs.tracker.anirena.exception;

public class WebScrapeException extends AnirenaException {

    public WebScrapeException() {}

    public WebScrapeException(String message) {
        super(message);
    }

    public WebScrapeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebScrapeException(Throwable cause) {
        super(cause);
    }

    public WebScrapeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
