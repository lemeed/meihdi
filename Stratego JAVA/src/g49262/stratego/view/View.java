package g49262.stratego.view;

import g49262.stratego.model.*;
import java.util.List;
import java.util.Scanner;

/**
 * The view of the game.
 *
 * @author Meihdi El Amouri
 */
public class View {

    Scanner in;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public View() {
        this.in = new Scanner(System.in);
    }

    /**
     * print a welcome message.
     */
    public void initialize() {
        System.out.println("Welcome To Stratego !");
    }

    /**
     * Print a quit message.
     */
    public void quit() {
        System.out.println("Bye Bye ");
    }

    /**
     * Print a error message.
     *
     * @param message the error message
     */
    public void displayError(String message) {
        System.out.println(message);
    }

    /**
     * ask to player to put a command.
     *
     * @return the text of the player.
     */
    public String askCommand() {
        System.out.println("Enter an order please");
        String s = in.nextLine();

        return s;
    }

    /**
     * print all the command that the player can put in the console.
     */
    public void displayHelp() {
        System.out.println("Usage:\n"+"   Write quit for leave the game\n"
                + "   You can also place a piece with 'select row column'\n"
                + "   With te command 'moves' you can see the available moves\n"
                + "   With the command 'apply number of move' you can do a move\n");
    }

    /**
     * Allows to print the selected piece.
     *
     * @param piece The selected piece.
     */
    public void displaySelected(Piece piece) {

        switch (piece.getRank()) {
            case 11:
                System.out.println("BOMBE " + piece.getColor() + " has been selected");
                break;
            case 2:
                System.out.println("ECLAIREUR " + piece.getColor() + " has been selected");
                break;
            case 0:
                System.out.println("FLAG " + piece.getColor() + " has been selected");
                break;
            case 9:
                System.out.println("GENERAL " + piece.getColor() + " has been selected");
                break;
            case 10:
                System.out.println("MARECHAL " + piece.getColor() + " has been selected");
                break;
            case 3:
                System.out.println("MINEUR " + piece.getColor() + " has been selected");
                break;
            case 1:
                System.out.println("SPY " + piece.getColor() + " has been selected");
                break;
        }

    }

    /**
     * Allows to print a game over message.
     */
    public void displayOver() {
        System.out.println("The game is over!");
    }

    /**
     * Allows to print the board.
     *
     * @param squares the instance of the board.
     * @param color the color of the current player.
     */
    @SuppressWarnings("empty-statement")
    public void displayBoard(Square[][] squares, String color) {

        System.out.println("col#   || | 00 | 01| 02| 03 | 04 | 05 ");
        System.out.println("=========================================");

        for (int i = 0; i <= 6; i++) {
            System.out.print(ANSI_RESET + "row" + "#0" + i + " || ");
            for (int j = 0; j <= 5; j++) {
                if (!squares[i][j].isFree()) {
                    if (squares[i][j].getPiece().getColor() == PlayerColor.BLUE && "BLUE".equals(color)) {
                        System.out.print(ANSI_BLACK + " |");
                        System.out.print(ANSI_BLUE + printSquare(squares[i][j]) + "");
                    } else if (squares[i][j].getPiece().getColor() == PlayerColor.RED && "RED".equals(color)) {
                        System.out.print(ANSI_BLACK + " |");
                        System.out.print(ANSI_RED + printSquare(squares[i][j]) + "");
                    } else {
                        printSquareWithColor(squares[i][j]);
                    }
                } else {
                    System.out.print(ANSI_BLACK + " |");
                    System.out.print(printSquare(squares[i][j]) + " ");
                }

            }
            System.out.println("");
        };
    }
    
    /**
     * Allows to print a square with color but no piece.
     * @param s the square.
     */
    public void printSquareWithColor(Square s){
        
        if (s.getPiece().getColor() == PlayerColor.RED){
            System.out.print(ANSI_BLACK + " |");
            System.out.print(ANSI_RED+"\u2b1b" + " ");
        }else{
            System.out.print(ANSI_BLACK + " |");
            System.out.print(ANSI_BLUE+"\u2b1b" + " ");
        }
            
    }

    /**
     * Allows to print a square in a board.
     *
     * @param square on the board.
     * @return the print square.
     */
    public String printSquare(Square square) {
        if (square.isFree() && square.getType() == SquareType.WATER) {
            return ANSI_CYAN + "\u2b1b";
        } else if (square.isFree()) {
            return "\u2b1b";
        } else {
            return printAPiece(square.getPiece());
        }
    }

    /**
     * Allows to print a piece in the board.
     *
     * @param p The piece who wants to print.
     * @return the name of the piece.
     */
    public String printAPiece(Piece p) {
        String namePiece = "";
        switch (p.getRank()) {
            case 11:
                namePiece = "BO";
                break;
            case 2:
                namePiece = "EC";
                break;
            case 0:
                namePiece = "DR";
                break;
            case 9:
                namePiece = "GE";
                break;
            case 10:
                namePiece = "MR";
                break;
            case 3:
                namePiece = "DM";
                break;
            case 1:
                namePiece = "SP";
                break;
        }
        return namePiece;
    }

    /**
     * Allows to print the possibles moves.
     *
     * @param moves the list of possibles moves.
     */
    public void displayMoves(List<Move> moves) {
        System.out.println(moves.size() + " move(s) possible(s)");
        if (moves.size() > 0) {
            for (int i = 0; i < moves.size(); i++) {
                System.out.println(i + " - can move to the line" + moves.get(i).getEnd().getRow()
                        + " and the row " + moves.get(i).getEnd().getColumn());
            }

        }
    }

    /**
     * Allows to print the current player.
     *
     * @param play the current player.
     */
    public void displayCurrentPlayer(Player play) {
        System.out.println("it's your turn player " + play.getColor());
    }

    /**
     * Allows to print the winner
     *
     * @param winner the winner(s) of the game
     */
    public void printWiner(List<Player> winner) {
        for (int i = 0; i < winner.size(); i++) {
            System.out.println(winner.get(i).getColor() + " won !!!!");
        }
    }
}
