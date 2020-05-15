package anagram.controller;

import anagram.model.Facade;
import anagram.view.ConsoleView;
import java.util.Objects;


/**
 * The <code> Quit </code> command ask to the model to close all connections and all files before end the program.
 * The view displays a farewell message before the end of the program.
 *
 * @author JLC Contact jlechien@he2b.be
 */
class Quit
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
    Quit(Facade model, ConsoleView view) {
        this.model =
        Objects.requireNonNull(model, "Modèle non initialisé  dans la commande");
        this.view =
        Objects.requireNonNull(view, "Vue non initialisée dans la commande");
    }

    @Override
    public void execute() {
      //  model.quit();
        view.quit();
        System.exit(0);

    }

}
