package anagram.controller;

import anagram.exception.ModelException;
import anagram.exception.ViewException;
import anagram.model.Facade;
import anagram.view.ConsoleView;
import java.util.Objects;

/**
 * The controller, accepts input and converts it to commands for the model or
 * view. The controller is responsible for responding to the user input and
 * perform interactions on the data model objects. The controller receives the
 * input, it validates the input and then performs the business operation that
 * modifies the state of the data model.
 *
 * @see
 * <a href="https://en.wikipedia.org/wiki/Model-view-controller"> Wikipedia</a>
 *
 * @author JLC contact jlechien@he2b.be
 */
public class ConsoleController {

    /**
     * The model to controll. MVC pattern.
     */
    private final Facade model;

    /**
     * The view to controll. MVC pattern.
     */
    private final ConsoleView view;

    /**
     *
     * Constructs the controller.
     *
     * @param model model to control.
     * @param view console view to control.
     *
     * @throws NullPointerException if the model or the view are null.
     */
    public ConsoleController(Facade model, ConsoleView view) {
        this.model
                = Objects.requireNonNull(model, "Modèle non initialisé  dans le controlleur");
        this.view
                = Objects.requireNonNull(view, "Vue non initialisée dans le controlleur");
    }

    /**
     * Initializes the application. Calls the model to initialize the
     * application, and the view to display it.
     */
    public void initialize() {
        try {
            model.initialize();
            view.initialize();
        } catch (ModelException e) {
            view.displayError(e.getMessage());
            view.quit();
            System.exit(0);
        }
    }

    /**
     * Starts the application.
     */
    public void start() {
        try {
            model.start();
            view.displayHelp();
            while (!model.isOver()) {
                view.displayStatistics(model.getNbWords(),
                        model.getNbRemainingWords(),
                        model.getNbSolvedWords(),
                        model.getNbUnsolvedWords());
                String scrambledWord = model.nextWord();
                while (!model.canAskNextWord()) {
                    view.displayScrambledWord(scrambledWord, model.getNbProposal());
                    askCommand();
                }
            }
        } catch (ModelException e) {
            view.displayError(e.getMessage());
        }
    }

    /**
     * Asks to the user what command to execute. This method was thought to be
     * use in a console environment.
     */
    private void askCommand() {
        boolean again = true;
        while (again) {
            String userRequest = null;
            try {
                userRequest = view.askCommand();
            } catch (ViewException ex) {
                view.displayError(ex.getMessage());
                new Quit(model, view).execute();
            }
            try {
                Command command = CommandFactory.build(userRequest, model, view);
                command.execute();
                again = false;
            } catch (Exception ex) {
                view.displayError(ex.getMessage());
            }
        }
    }
}
