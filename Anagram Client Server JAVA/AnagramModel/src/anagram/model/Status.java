package anagram.model;

/**
 * Status of a word in the anagram game.
 *
 * @author jlc
 */
public enum Status {

    /**
     * Set to a word find out by the user.
     */
    SOLVED,
    /**
     * Set to a word pass by the user.
     */
    UNSOLVED,
    /**
     * Set to a word by default.
     */
    UNREAD,
    /**
     * Set to a word when the word is in play.
     */
    READ;
}
