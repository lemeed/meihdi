package anagram.controller;

/**
 * Interface used to implement the command pattern.
 *
 * The command pattern is a behavioral design pattern in which an object is used
 * to encapsulate all information needed to perform an action or trigger an
 * event at a later time. In this application the command pattern is used to
 * simulate the command given by the user in a command line environment.
 *
 * @see
 * <a href="https://en.wikipedia.org/wiki/Command_pattern"> Wikipedia</a>
 *
 * @author JLC contact jlechien@he2b.be
 */
interface Command {

    /**
     * Executes the algorithm associate to a command.
     */
    void execute();
}
