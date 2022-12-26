package exception;

public class ExistingTitleException extends NotePadException {
    public ExistingTitleException(String message) {
        super(message);
    }
}