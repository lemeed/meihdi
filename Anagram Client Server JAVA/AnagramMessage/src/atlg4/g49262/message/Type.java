package atlg4.g49262.message;

/**
 *
 * @author <49282@etu.he2b.be>
 */
public enum Type {

    /**
     * Message with the profile of a specific user.
     */
    PROFILE,
    /**
     * General message send between two connected users.
     */
    MAIL_TO,
    /**
     * Message with the list of all connected users.
     */
    MEMBERS,
    
    /**
     * The word that the player have to find.
     */
    PROPOSITION,
    /**
     * The answer of player
     */
    CHECK_ANSWER,
    /**
     * If the player pass the word.
     */
    PASS,
    /**
     * The number of try for a word.
     */
    NB_TRY,
    /**
     * The number of found word.
     */
    NB_FOUND,
    /**
     * The number of Fail word.
     */
    NB_FAIL,
    /**
     * The number of left word.
     */
    NB_LEFT,
    /**
     * is the game over.
     */
    IS_OVER,
    /**
     * Allows to fill the choicebox.
     */
    FILL,
    /**
     * Allows to get number of word found for user.
     */
    NB_FOUND_USER,
    /**
     *Print the number of correct word.
     */
    NB_PRINT,
    /**
     *Allows to alert if the player pass. 
     */
    ALERT_WORD,
    /**
     * Allows to print an alert message if the player already exist. 
     */
    ALERT_PLAYER,
    /**
     * Allows to print the score. 
     */
    NB_PRINT_SCORE, INDICE, PRINT_WORD_HISTORIC;
    
}
