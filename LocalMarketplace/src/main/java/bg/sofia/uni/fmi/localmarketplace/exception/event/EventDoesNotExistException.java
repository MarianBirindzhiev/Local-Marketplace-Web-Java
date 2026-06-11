package bg.sofia.uni.fmi.localmarketplace.exception.event;

public class EventDoesNotExistException extends RuntimeException {

    public EventDoesNotExistException(String message) {
        super(message);
    }

    public EventDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
