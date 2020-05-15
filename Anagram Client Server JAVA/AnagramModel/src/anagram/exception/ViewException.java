package anagram.exception;

/**
 * Exception throwed when the view failed.
 *
 * @author jlc
 */
public class ViewException extends Exception {

    /**
     * Creates a new instance of <code>ViewException</code> without detail
     * message.
     */
    public ViewException() {
    }

    /**
     * Constructs an instance of <code>ViewException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ViewException(String msg) {
        super(msg);
    }
}
