package fr.antoine.fraudify.exceptions;

public class MetaDataDownloadException extends RuntimeException{
    public MetaDataDownloadException() {
        super();
    }

    public MetaDataDownloadException(String message) {
        super(message);
    }

    public MetaDataDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetaDataDownloadException(Throwable cause) {
        super(cause);
    }
}
