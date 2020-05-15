package anagram.exception;

/**
 * Exception throwed when DTO handling failed.
 *
 * @author jlc
 */
public class BusinessException extends Exception {

    /**
     * Creates a new instance of <code>BusinessException</code> without detail
     * message.
     */
    public BusinessException() {
    }

    /**
     * Constructs an instance of <code>BusinessException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BusinessException(String msg) {
        super(msg);
    }
}
