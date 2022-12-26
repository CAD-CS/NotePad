package exception;

public class DoesNotExistException extends NotePadException {
    public DoesNotExistException(String msg) {
        super(msg);
    }
}
