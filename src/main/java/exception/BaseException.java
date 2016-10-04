package exception;

/**
 * Created by Mephalay on 10/4/2016.
 */
public abstract class BaseException extends Exception {
    protected final String exceptionCode;
    protected final String exceptionPrompt;
    protected final Throwable innerException;


    public BaseException(String message, String exceptionCode, String exceptionPrompt, Throwable innerException) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.exceptionPrompt = exceptionPrompt;
        this.innerException = innerException;
    }


    public final String getExceptionCode() {
        return exceptionCode;
    }

    public final String getExceptionPrompt() {
        return exceptionPrompt;
    }

    public final Throwable getInnerException() {
        return this.innerException;
    }

}
