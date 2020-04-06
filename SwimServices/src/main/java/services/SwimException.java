package services;


public class SwimException extends Exception{
    public SwimException() {
    }

    public SwimException(String message) {
        super(message);
    }

    public SwimException(String message, Throwable cause) {
        super(message, cause);
    }
}
