package fr.antoine.fraudify.exceptions;

public class AlreadyExistTrackException extends Exception {
    public AlreadyExistTrackException() {
        super();
    }


    public AlreadyExistTrackException(String message) {
        super(message);
    }


    public AlreadyExistTrackException(String message, Throwable cause) {
        super(message, cause);
    }


    public AlreadyExistTrackException(Throwable cause) {
        super(cause);
    }
}
