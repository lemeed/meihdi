package anagram.controller;

import anagram.exception.ModelException;
import anagram.model.Facade;
import anagram.view.ConsoleView;
import java.util.Objects;

/**
 * The <code> Check </code> command pass to the next word. 
 * The view displays the answer,.
 *
 * @author JLC Contact jlechien@he2b.be
 */
class Pass
        implements Command {

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
     * Constructs the Quit command.
     *
     * @param model model where the connections are to be closed.
     *
     * @param view view where a farewell message is displayed.
     *
     * @throws NullPointerException if the model or the view are null.
     */
    Pass(Facade model, ConsoleView view) {
        this.model
                = Objects.requireNonNull(model, "Modèle non initialisé  dans la commande");
        this.view
                = Objects.requireNonNull(view, "Vue non initialisée dans la commande");
    }

    @Override
    public void execute() {
        try {
            String answer = model.pass();
            view.displayAnswer(answer);
        } catch (ModelException ex) {
            view.displayError(ex.getMessage());
        }

    }

}
