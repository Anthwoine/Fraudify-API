package fr.antoine.fraudify.exceptions;

public class TrackAlreadyInPlaylistException extends Exception {
    public TrackAlreadyInPlaylistException(String message) {
        super(message);
    }

    public TrackAlreadyInPlaylistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrackAlreadyInPlaylistException(Throwable cause) {
        super(cause);
    }

    public TrackAlreadyInPlaylistException() {
        super();
    }
}
