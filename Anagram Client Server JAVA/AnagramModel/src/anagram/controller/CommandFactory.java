package anagram.controller;

import anagram.model.Facade;
import anagram.view.ConsoleView;

/**
 * This factory build the command associate to a request given by the user.
 *
 * The factory method pattern is a creational pattern that uses factory methods
 * to deal with the problem of creating objects without having to specify the
 * exact class of the object that will be created.
 *
 * @see
 * <a href="https://en.wikipedia.org/wiki/Factory_method_pattern"> Wikipedia</a>
 *
 * @author JLC contact jlechien@he2b.be
 */
public final class CommandFactory {

    /**
     * Builds the command associate to the request given by the user.
     *
     * @param userRequest request from the user.
     *
     * @param model model's facade to call when you execute the command.
     *
     * @param view view view to update when you execute the command.
     *
     * @return the command associate to the request given by the user.
     */
    static Command build(String userRequest, Facade model, ConsoleView view) {
        Command command;
        String delims = "[ ]+";
        String[] tokens = userRequest.split(delims);
        switch (tokens[0]) {
            case "check":
                if (tokens.length != 2) {
                    throw new IllegalArgumentException("Nombre de paramètre incorrect " + tokens.length);
                }
                String proposal = tokens[1];
                command = new Check(model, view, proposal);
                break;
            case "pass":
                command = new Pass(model, view);
                break;                
            case "quit":
                command = new Quit(model, view);
                break;
            default:
                command = new Help(view);
                break;
        }

        return command;
    }

    /**
     *
     * Constructs the CommandFactory.
     *
     * Be aware of the private visibility.
     */
    private CommandFactory() {

    }
}
