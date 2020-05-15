package anagram.controller;

import anagram.exception.ModelException;
import anagram.model.Facade;
import anagram.view.ConsoleView;
import java.util.Objects;

/**
 * The <code> Check </code> command ask to the model if a proposal is an
 * anagram. The view displays some congratulations or some confort messages.
 *
 * @author JLC Contact jlechien@he2b.be
 */
class Check implements Command {

    /**
     * The model to controll. MVC pattern.
     */
    private final Facade model;
    /**
     * The view to controll. MVC pattern.
     */
    private final ConsoleView view;

    /**
     * The user's proposal for an anagram.
     */
    private final String proposal;

    /**
     *
     * Constructs the <code> Check </code> command.
     *
     * @param model the anagram game.
     *
     * @param view view where the result is displayed.
     * @param proposal the user's proposal for an anagram.
     *
     * @throws NullPointerException if some parameters are null.
     */
    Check(Facade model, ConsoleView view, String proposal) {
        this.model
                = Objects.requireNonNull(model, "Modèle non initialisé  dans la commande");
        this.view
                = Objects.requireNonNull(view, "Vue non initialisée dans la commande");
        this.proposal
                = Objects.requireNonNull(proposal, "Réponse non initialisée dans la commande");
        if (proposal.isEmpty()) {
            throw new IllegalArgumentException("Réponse vide dans la commande");
        }
    }

    @Override
    public void execute() {
        try {
            boolean result = model.propose(proposal);
            if (result) {
                view.displayCongratulations();
            } else {
                view.displayTryAgain();
            }
        } catch (ModelException ex) {
            view.displayError(ex.getMessage());
        }

    }

}
