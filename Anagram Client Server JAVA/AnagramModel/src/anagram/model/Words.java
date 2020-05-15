package anagram.model;

import anagram.exception.ModelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Container of Word.
 *
 * @author jlc
 */
class Words {

    /**
     * The list of all words in game.
     */
    private final List<Word> words;
    /**
     * Iterator of the list of word.
     */
    private Iterator<Word> iterator;

    /**
     * The boolean is true if the list is closed and no word can be add.
     */
    private boolean full;

    /**
     * Constructs the container <code> Words </code>. This container starts with
     * no word..
     */
    Words() {
        words = new ArrayList<>();
        full = false;
    }

    /**
     * Constructs the container <code> Words </code>. This container starts with
     * a list of words given. The container is closed.
     *
     * @param otherList the list of words to copy into this container.
     */
    Words(List<Word> otherList) {
        words = new ArrayList<>(otherList);
        full = true;
    }

    /**
     * Adds a word into the container if the container is not closed.
     *
     * @param word a word to add.
     * @throws anagram.exception.ModelException if the container is closed.
     */
    void add(Word word) throws ModelException {
        if (full) {
            throw new ModelException("La liste des mots est complète");
        }
        Objects.requireNonNull(word, "Aucun mot donné en paramètre");
        words.add(word);
    }

    /**
     * Shuffles the words.
     *
     * @throws anagram.exception.ModelException if the list is not closed.
     */
    void shuffle() throws ModelException {
        if (!full) {
            throw new ModelException("La liste des mots n'est pas complète");
        }
        Collections.shuffle(words);
        iterator = words.iterator();
    }

    /**
     * Returns true if the container is closed and false otherwise.
     *
     * @return true if the container is closed and false otherwise.
     */
    public boolean isFull() {
        return full;
    }

    /**
     * Sets the boolean to true if the container is closed and to false
     * otherwise.
     *
     * @param full true if the container is closed and to false otherwise.
     */
    void setFull(boolean full) {
        this.full = full;
    }

    /**
     * Returns the next word of the container to play. An ModelException is
     * throwed if the container is not full and a NoSuchElementException if all
     * the words are played.
     *
     * @return the next word of the container to play.
     *
     * @throws anagram.exception.ModelException if the container is not closed.
     */
    Word next() throws ModelException {
        if (!full) {
            throw new ModelException("La liste des mots n'est pas complète");
        }
        return iterator.next();
    }

    /**
     * Returns true if a next word exist in the container and false otherwise.
     *
     * @return true if a next word exist in the container and false otherwise.
     *
     * @throws anagram.exception.ModelException if the container is not closed.
     */
    boolean hasNext() throws ModelException {
        if (!full) {
            throw new ModelException("La liste des mots n'est pas complète");
        }
        return iterator.hasNext();
    }

    /**
     * Returns true if the container is empty and false otherwise.
     *
     * @return true if the container is empty and false otherwise.
     */
    boolean isEmpty() {
        return words.isEmpty();
    }

    /**
     * Returns the amount of words in the container.
     *
     * @return the amount of words in the container.
     */
    int size() {
        return words.size();
    }

    /**
     * Returns the amount of remaining words in the container. A remaining word
     * has an UNREAD status.
     *
     * @return the amount of remaining words in the container.
     */
    int getNbRemainingWords() {
        return (int) words.stream()
                .filter(Word::isUnread)
                .count();
    }

    /**
     * Returns the amount of solved words in the container. A solved word has an
     * SOLVED status.
     *
     * @return the amount of solved words in the container.
     */
    int getNbSolvedWords() {
        return (int) words.stream()
                .filter(Word::isSolved)
                .count();
    }

    /**
     * Returns the amount of unsolved words in the container. An unsolved word
     * has an UNSOLVED status.
     *
     * @return the amount of unsolved words in the container.
     */
    int getNbUnsolvedWords() {
        return (int) words.stream()
                .filter(Word::isUnSolved)
                .count();
    }
}
