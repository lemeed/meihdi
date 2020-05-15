package anagram.model;

import anagram.business.AdminFacade;
import anagram.dto.WordDto;
import anagram.exception.BusinessException;
import anagram.exception.ModelException;
import anagram.model.converter.WordDtoConverter;
import java.util.List;

/**
 * The anagram game.
 *
 */
public class Model implements Facade {

    /**
     * All the words to play.
     */
    private Words words;
    /**
     * The current word in play.
     */
    private Word currentWord;
    /**
     * A converter from WordDto to Word. This converter is used to transform the
     * data read from a file to a real object (with some methods).
     */
    private final WordDtoConverter wordDtoConverter;

    /**
     * Constructs the <code> Model </code> of anagram game. The word converter
     * is ready.
     */
    public Model() {
        wordDtoConverter = new WordDtoConverter();
    }
     
    @Override
    public void initialize() throws ModelException {
        List<WordDto> wordDtos;
        try {
            wordDtos = AdminFacade.getAllWords();
        } catch (BusinessException ex) {
            throw new ModelException(ex.getMessage());
        }
        List<Word> wordList = wordDtoConverter.convert(wordDtos);
        words = new Words(wordList);
    }

    @Override
    public void start() throws ModelException {
        if (words == null || words.isEmpty()) {
            throw new ModelException("la liste de mots est vide");
        }
        words.shuffle();
    }

    @Override
    public String nextWord() throws ModelException {
        if (isOver()) {
            throw new ModelException("Le jeu est terminé");
        }
        if (currentWord != null && currentWord.isUnread()) {
            throw new ModelException("Un mot est encore en cours de jeu.");
        }
        currentWord = words.next();
        currentWord.setRead();
        return currentWord.getScramble();
    }

    @Override
    public String getCurrentWord() {
        return currentWord.getText();
    }

    
    @Override
    public boolean propose(String proposal) throws ModelException {
        if (currentWord == null) {
            throw new ModelException("Aucun mot en jeu");
        }
       
        
        boolean isAnagram = currentWord.isAnagram(proposal);
        if (isAnagram) {
            currentWord.solved();
        }
        currentWord.addProposal();
        return isAnagram;
    }

    @Override
    public String pass() throws ModelException {
        if (currentWord == null) {
            throw new ModelException("Aucun mot en jeu");
        }
        String answer = currentWord.getText();
        currentWord.unsolved();
        return answer;
    }

    @Override
    public boolean isOver() throws ModelException {
        if (words == null || words.isEmpty()) {
            throw new ModelException("la liste de mots est vide");
        }
        return !words.hasNext();
    }

    @Override
    public boolean canAskNextWord() {
        return currentWord == null
                || currentWord.isSolved()
                || currentWord.isUnSolved();
    }

    @Override
    public int getNbWords() throws ModelException {
        if (words == null || words.isEmpty()) {
            throw new ModelException("la liste de mots est vide");
        }
        return words.size();
    }

    @Override
    public int getNbRemainingWords() throws ModelException {
        if (words == null || words.isEmpty()) {
            throw new ModelException("la liste de mots est vide");
        }
        return words.getNbRemainingWords();
    }

    @Override
    public int getNbSolvedWords() throws ModelException {
        if (words == null || words.isEmpty()) {
            throw new ModelException("la liste de mots est vide");
        }
        return words.getNbSolvedWords();
    }

    @Override
    public int getNbUnsolvedWords() throws ModelException {
        if (words == null || words.isEmpty()) {
            throw new ModelException("la liste de mots est vide");
        }
        return words.getNbUnsolvedWords();
    }

    @Override
    public int getNbProposal() throws ModelException {
        if (currentWord == null) {
            throw new ModelException("Aucun mot en jeu");
        }
        return currentWord.getNbProposal();
    }

}
