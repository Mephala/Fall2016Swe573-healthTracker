package exception;

/**
 * Created by Mephalay on 10/4/2016.
 */
public class RegistrationException extends BaseException {
    public RegistrationException(String exceptionPrompt, String exceptionCode, String message, Throwable t) {
        super(message, exceptionCode, exceptionPrompt, t);
    }
}
