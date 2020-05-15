package anagram.dto;

/**
 * The data tranfert object for a Word in the Anagram game.
 *
 * @author jlc
 */
public class WordDto extends EntityDto<Integer> {

    /**
     * The text of the word.
     */
    private String text;

    /**
     * Constructs the <code> WordDto </code>.
     *
     * @param id ID fo the word.
     * @param text text of the word.
     */
    public WordDto(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    /**
     * Return the text of the word.
     *
     * @return the text of the word.
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text of the word.
     *
     * @param text the text of the word.
     */
    public void setText(String text) {
        this.text = text;
    }

}
