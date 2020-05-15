package anagram.model;

import anagram.exception.ModelException;

/**
 * Facade of the Anagram game.
 *
 * An anagram is a word formed by rearranging the letters of a different word,
 * typically using all the original letters exactly once. For example, the word
 * binary can be rearranged into brainy.
 *
 * @see
 * <a href="https://en.wikipedia.org/wiki/Facade"> Link to a description of the
 * design pattern facade  </a>
 *
 *
 * @author JLC Contact jlechien@he2b.be
 */
public interface Facade {

    /**
     * Reads all the words from a data source and fills the Words container.
     *
     * @throws ModelException if the datas aren't readable.
     */
    void initialize() throws ModelException;

    /**
     * Shuffle the list words.
     *
     * @throws ModelException if the container of words is empty.
     */
    void start() throws ModelException;

    /**
     * Returns the next word of the container to play. An ModelException is
     * throwed if the container is not full or if a word is still in game and a
     * NoSuchElementException if all the words are played.
     *
     * @return the next word of the container to play.
     *
     * @throws ModelException if the container is not full or if a word is still
     * in game.
     */
    String nextWord() throws ModelException;

    /**
     * Returns true if the input string is an anagram of the current word in
     * play and false otherwise. The test is not case sensitive.
     *
     * @param proposal the anagram prppose by the user.
     * @return true if the input string is an anagram of the current word in
     * play and false otherwise.
     * @throws ModelException if the proposal is null or empty.
     */
    boolean propose(String proposal) throws ModelException;

    /**
     * Turns the status of the current word into UNSOLED and returns the answer.
     *
     * @return the answer.
     *
     * @throws ModelException if no word is in play.
     */
    String pass() throws ModelException;

    /**
     * Return true if the game is over and false otherwise. The game is over if
     * no word has the UNREAD status.
     *
     * @return true if the game is over and false otherwise.
     *
     * @throws anagram.exception.ModelException if the container is not closed.
     */
    boolean isOver() throws ModelException;

    /**
     * Return false if a word is in play and true otherwise.
     *
     * @return false if a word is in play and true otherwise.
     */
    boolean canAskNextWord();

    /**
     * Returns the amount of words in the container.
     *
     * @return the amount of words in the container.
     * @throws ModelException if the container of words is empty.
     */
    int getNbWords() throws ModelException;

    /**
     * Returns the amount of remaining words in the container. A remaining word
     * has an UNREAD status.
     *
     * @return the amount of remaining words in the container.
     * @throws ModelException if the container of words is empty.
     */
    int getNbRemainingWords() throws ModelException;

    /**
     * Returns the amount of solved words in the container. A solved word has an
     * SOLVED status.
     *
     * @return the amount of solved words in the container.
     * @throws ModelException if the container of words is empty.
     */
    int getNbSolvedWords() throws ModelException;

    /**
     * Returns the amount of unsolved words in the container. An unsolved word
     * has an UNSOLVED status.
     *
     * @return the amount of unsolved words in the container.
     * @throws ModelException if the container of words is empty.
     */
    int getNbUnsolvedWords() throws ModelException;

    /**
     * Returns the amount of user's proposal for the current word.
     *
     * @return the amount of user's proposal for the current word.
     * @throws ModelException if the container of words is empty.
     */
    int getNbProposal() throws ModelException;
    
    String getCurrentWord() ;
}



