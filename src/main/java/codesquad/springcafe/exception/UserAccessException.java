package codesquad.springcafe.exception;

public class UserAccessException extends RuntimeException {
    public UserAccessException(String message) {
        super(message);
    }
}
