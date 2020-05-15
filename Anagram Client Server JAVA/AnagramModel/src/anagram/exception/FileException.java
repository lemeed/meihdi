package anagram.exception;

/**
 *
 * @author jlc
 */
public class FileException extends Exception {

    /**
     * Creates a new instance of <code>FileException</code> without detail
     * message.
     */
    public FileException() {
    }

    /**
     * Constructs an instance of <code>FileException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public FileException(String msg) {
        super(msg);
    }
}
