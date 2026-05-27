package bg.sofia.uni.fmi.localmarketplace.exception.order;

public class OrderDoesNotExistException extends RuntimeException {
    public OrderDoesNotExistException(String message) {
        super(message);
    }

    public OrderDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
