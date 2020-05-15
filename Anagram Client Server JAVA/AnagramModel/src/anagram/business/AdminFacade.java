package anagram.business;

import anagram.dto.WordDto;
import anagram.exception.BusinessException;
import anagram.exception.FileException;
import anagram.file.WordsFileReader;
import static anagram.file.WordsFileReader.DEFAULT_READ_URL_FILE;
import java.util.List;

/**
 * Facade to the data level.
 *
 * @author jlc
 */
public final class AdminFacade {

    /**
     * Return the list of all words available.
     *
     * @return the list of all words available.
     * @throws BusinessException if the datas aren't readable.
     */
    public static List<WordDto> getAllWords() throws BusinessException {
        try {

            return WordsFileReader.readFile(DEFAULT_READ_URL_FILE);
        } catch (FileException eData) {
            String msg = eData.getMessage();
            try {
                msg = eData.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des mots inaccessible! \n" + msg);
            }
        }

    }

    /**
     *
     * Constructs the <code> Anagram </code> application.
     *
     * Be aware of the private visibility.
     */
    private AdminFacade() {

    }
}
