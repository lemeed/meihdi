/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.view;

import blokus.model.Game;
import blokus.model.Model;
import blokus.model.Piece;
import java.util.Scanner;
import java.util.List;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class View {

    // APA : A quoi sert-il ?
    private Scanner in;

    /**
     * verify if is possible to add a new explorer.
     *
     * @return true if is possible.
     */
    public boolean isThereNewExplorerToAdd() {
        System.out.println("Do you want to add someone else ?(true/false)");
        checkTheAnswer();
        return in.nextBoolean();
    }

    /**
     * verify if the answer of the user is true or false.
     */
    public void checkTheAnswer() {
        boolean correct = false;
        while (!correct) {
            if (this.in.hasNextBoolean()) {
                correct = true;
            } else {
                System.out.println("enter true of false please!");
                this.in.next();
            }
        }

    }

    /**
     * print welcome to users.
     */
    public void printWelcome() {
        System.out.println("Bienvenue sur le jeux Blokus!>n" + " Pour connaitre les commandes tapez 'help' ");
    }

    /**
     * print an help panel for the beginner.
     */
    private static void printHelp() {

        System.out.println("****** EXEMPLE ******\n"
                + "Show : affiche le board \n"
                + "Stock : Permet de consulter votre stock \n"
                + "Play n i j : play suivi du numéro de la pièce et des \n"
                + "coordonnées (i,j) sur le board \n"
                + "exit for exit the application \n");
    }

    /**
     * print the stock of the player.
     *
     * @param game
     * @return the stock of player in string.
     */
    public String stockDisplay(Game game) {
        List<Piece> stock = game.currentPlayer().getStock().getStock();
        String stocks = "";
        stocks += game.currentPlayer() + ": \n";
        for (int i = 0; i < stock.size(); i++) {
            if (stock.get(i) != null) {
                stocks += (i + 1) + " ";
            }
        }
        return stocks;
    }

    /**
     * Print the board of the game.
     *
     * @param game
     * @return the board in string.
     */
    public static String boardDisplay(Model game) {
        String board = "";
        // APA : Il faut utilisre un stringBuilder
        int k = 0;
        int size = game.getBlokusBoard().length - 1;
        for (int i = 0; i < game.getBlokusBoard().length; i++) {
            if (i < 10) {
                board += " |" + i + "";

            } else {
                board += "|" + i + "";
            }
        }
        board += "|\n";
        for (int i = 0; i < game.getBlokusBoard().length; i++) {
            board += " |";
            for (int j = 0; j < game.getBlokusBoard().length; j++) {
                if (game.getBlokusBoard()[j][i + size].getPlayer() != null) {
                    board += game.getBlokusBoard()[j][i + size].getPlayer().getName().charAt(0) + " |";

                } else {

                    board += "  |";
                }

            }
            board += i + size + "\n";
            size = size - 2;

        }
        return board;
    }

}
