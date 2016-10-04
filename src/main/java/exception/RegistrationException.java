package exception;

/**
 * Created by Mephalay on 10/4/2016.
 */
public class RegistrationException extends BaseException {
    public RegistrationException(String message, String exceptionCode, String exceptionPrompt, Throwable t) {
        super(message, exceptionCode, exceptionPrompt, t);
    }
}
