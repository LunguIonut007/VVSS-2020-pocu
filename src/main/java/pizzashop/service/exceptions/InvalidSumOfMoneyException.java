package pizzashop.service.exceptions;

public class InvalidSumOfMoneyException extends RuntimeException {
    public InvalidSumOfMoneyException(String message) {
        super(message);
    }
}
