package validators;

import java.util.List;

public class ValidationException extends RuntimeException{
    public ValidationException(List<String> message) {
        super(String.valueOf(message));
    }

    public ValidationException(String message) {
        super(message);
    }
}
