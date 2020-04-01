package pizzashop.service.exceptions;

public class TableNotExistentException extends RuntimeException {
    public TableNotExistentException(String message) {
        super(message);
    }
}
