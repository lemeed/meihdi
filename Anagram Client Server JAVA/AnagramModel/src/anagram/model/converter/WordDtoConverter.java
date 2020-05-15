package anagram.model.converter;

import anagram.dto.WordDto;
import anagram.model.Word;

/**
 * Turns a WordDto object into a Word Object.
 *
 * @author jlc
 */
public class WordDtoConverter implements GenericConverter<WordDto, Word> {

    @Override
    public Word apply(WordDto input) {
        Word output = new Word(input.getText());
        return output;
    }

}
