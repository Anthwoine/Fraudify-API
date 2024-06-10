package fr.antoine.fraudify.exceptions;

public class TrackDownloadException extends Exception {
    public TrackDownloadException() {
        super();
    }

    public TrackDownloadException(String message) {
        super(message);
    }

    public TrackDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrackDownloadException(Throwable cause) {
        super(cause);
    }
}
