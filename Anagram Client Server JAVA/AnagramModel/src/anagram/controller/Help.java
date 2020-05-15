package anagram.controller;

import anagram.view.ConsoleView;
import java.util.Objects;


/**
 * The <code> Help </code> command displays some help to the user.
 * 
 * @author JLC contact jlechien@he2b.be
 */
class Help
        implements Command {

    /**
     * The view to controll. MVC pattern.
     */
    private final ConsoleView view;

    /**
     *
     * Constructs the Help command.
     *
     * @param view view where the help is displayed.
     *
     * @throws NullPointerException if the view is null.
     */    
    Help( ConsoleView view) {
        this.view =
        Objects.requireNonNull(view, "Vue non initialisée dans la commande");
    }

    @Override
    public void execute() {
        view.displayHelp();
    }

}
