package anagram;

import anagram.controller.ConsoleController;
import anagram.model.Facade;
import anagram.model.Model;
import anagram.view.ConsoleView;

/**
 * The <code> Anagram </code> application.
 *
 * @author JLC contact jlechien@he2b.be
 */
public final class Anagram {

    /**
     * Entry points to the <code> Anagram </code> application.
     *
     * @param args no arguments needed.
     */
    public static void main(String[] args) {
        Facade model = new Model();
        ConsoleView view = new ConsoleView();
        ConsoleController controller = new ConsoleController(model, view);
        controller.initialize();
        controller.start();
    }

    /**
     *
     * Constructs the <code> Anagram </code> application.
     *
     * Be aware of the private visibility.
     */
    private Anagram() {
    }
}
