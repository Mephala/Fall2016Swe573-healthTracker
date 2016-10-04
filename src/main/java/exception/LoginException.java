package exception;

/**
 * Created by Mephalay on 10/4/2016.
 */
public class LoginException extends BaseException {
    public LoginException(String message, String exceptionCode, String exceptionPrompt, Throwable innerException) {
        super(message, exceptionCode, exceptionPrompt, innerException);
    }
}
