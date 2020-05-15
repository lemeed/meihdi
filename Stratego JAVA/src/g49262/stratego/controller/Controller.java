package g49262.stratego.controller;

import g49262.stratego.model.Model;
import g49262.stratego.view.View;

/**
 * This is the controller of the game.
 *
 * @author Meihdi El Amouri
 */
public class Controller {

    Model game;
    View view;

    /**
     * The constructor of the controller.
     *
     * @param game the instance of game.
     * @param view the view of stratego.
     */
    public Controller(Model game, View view) {
        this.game = game;
        this.view = view;
    }

    /**
     * Initialize the board of the game.
     */
    public void initialize() {
        game.initialize();

    }

    /**
     * Allows to start and end the game.
     */
    public void startGame() {

        initialize();
        boolean again = true;

        game.start();

        view.displayHelp();
        view.displayBoard(game.getBoard(), game.getCurrent().getColor().getValue());
        view.displayCurrentPlayer(game.getCurrent());

        while (!game.isOver()) {

            try {

                String command = view.askCommand();

                String[] commandTable = command.split(" ");

                request(commandTable);

            } catch (NumberFormatException ex) {
                System.out.println("You don't respect the types of characters ! ");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("you don't respect the instruction please enter a right number");
            } catch (NullPointerException ex) {
                System.out.println("No piece selected");
            }catch (IllegalStateException ex) {
                System.out.println("There's no piece in this square");
            }

        }
        view.printWiner(game.getWinners());

    }

    /**
     * Allows to do the command that player put.
     *
     * @param commandTable the table that contains the instructions.
     * @return true if we can continue the game.
     */
    private void request(String[] commandTable) {

        switch (commandTable[0].toLowerCase()) {

            case "quit":

                view.quit();
                System.exit(0);

            case "select":
                if (commandTable[1] == null || commandTable[2] == null) {
                    throw new IllegalArgumentException("");
                }
                game.select(Integer.parseInt(commandTable[1]), Integer.parseInt(commandTable[2]));
                view.displaySelected(game.getSelected());
                break;

            case "moves":

                view.displayMoves(game.getMoves());
                break;

            case "apply":

                game.apply(game.getMoves().get(Integer.parseInt(commandTable[1])));

                view.displayBoard(game.getBoard(), game.getCurrent().getColor().getValue());
                view.displayCurrentPlayer(game.getCurrent());

        }

    }

}
