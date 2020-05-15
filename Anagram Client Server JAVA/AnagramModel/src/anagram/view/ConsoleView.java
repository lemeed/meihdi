package anagram.view;

import anagram.exception.ViewException;
import java.util.Scanner;

/**
 * The console view displays all the application's informations in a console.
 *
 * The view means only presentation of the model.
 *
 * @see
 * <a href="https://en.wikipedia.org/wiki/Model-view-controller"> Wikipedia</a>
 *
 * @author JLC Contact jlechien@he2b.be
 */
public class ConsoleView {

    /**
     * Prints a text in a console. If you want to indent the text with 2 blank
     * spaces, you just update this method instead of all the display method.
     *
     * @param text text to print in the console.
     */
    static final void print(String text) {
        System.out.println(text);
    }

    /**
     * The user can give his input from this scanner.
     */
    private final Scanner input;

    /**
     * Constructs the console view. The input used is the input stream connected
     * to the console (System.in).
     *
     */
    public ConsoleView() {
        input = new Scanner(System.in, "UTF-8");

    }

    /**
     * Prints a welcome message.
     */
    public void initialize() {
        print("Bienvenue au jeu d'anagramme");
    }

    /**
     * Prints a farewell message.
     */
    public void quit() {
        print("\nBye Bye\n");
    }

    /**
     * Prints an error message.
     *
     * @param message the error message.
     */
    public void displayError(String message) {
        print("\nErreur : " + message);
    }

    /**
     * Prints the help message.
     */
    public void displayHelp() {
        print("\nUsage :");
        print("\ttaper check <word> pour proposer votre réponse");
        print("\ttaper pass pour passer le mot courant");
        print("\ttaper quit pour arrêter le programme");
        print("\n");

    }

    /**
     * Asks a command from the user.
     *
     * @return the request of the user.
     *
     * @throws ViewException if the Scanner is not initialized.
     */
    public String askCommand() throws ViewException {
        if (input == null) {
            throw new ViewException("Aucun input Scanner déclaré");
        }
        print("\nEntrez votre commande ");
        return input.nextLine();
    }

    /**
     * Prints the scrambled word given and the amount of proposal send by the
     * player. The user has to find the original word.
     *
     * @param scrambledWord the scrambled version of the word.
     * @param nbProposal the amout of proposal for the current word.
     */
    public void displayScrambledWord(String scrambledWord, int nbProposal) {
        print("Quel est l'anagramme de " + scrambledWord);
        print(nbProposal + " proposition(s) pour ce mot.");
    }

    /**
     * Prints some congratulations if the user wins.
     */
    public void displayCongratulations() {
        print("Bravo !! ");
    }

    /**
     * Prints some comfort message if the user fails.
     */
    public void displayTryAgain() {
        print("Mauvaise réponse. Essaie encore ");
    }

    /**
     * Prints the answer of the anagram game.
     *
     * @param answer the answer of the anagram game.
     */
    public void displayAnswer(String answer) {
        print("La réponse est " + answer);
    }

    /**
     * Prints some statistics about the game.
     *
     * @param nbWords the amount of words to play.
     * @param nbRemainingWords the amount of remaining words to play.
     * @param nbSolvedWords the amount of solved words.
     * @param nbUnsolvedWords the amount of unsolved words.
     */
    public void displayStatistics(int nbWords, int nbRemainingWords, int nbSolvedWords, int nbUnsolvedWords) {
        print("Il reste " + nbRemainingWords + " mot(s) à deviner sur les " + nbWords + " mots disponibles");
        print("Vous avez trouvé " + nbSolvedWords + " mot(s) et échoué sur " + nbUnsolvedWords + " mot(s) ");
    }

}
