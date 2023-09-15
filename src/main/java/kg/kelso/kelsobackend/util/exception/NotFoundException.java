package kg.kelso.kelsobackend.util.exception;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super("Запрошенный элемент не найден.");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
