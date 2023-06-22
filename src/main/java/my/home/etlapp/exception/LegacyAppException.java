package my.home.etlapp.exception;

public class LegacyAppException extends RuntimeException {
    public LegacyAppException(String message) {
        super(message);
    }

    public LegacyAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
