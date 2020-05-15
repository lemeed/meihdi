/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.controller;

import blokus.model.Game;
import blokus.model.Model;
import blokus.model.Point;
import static blokus.view.View.boardDisplay;
import blokus.viewFx.BlokusMain;
import java.util.Scanner;

/**
 * The Display in the console.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Controller {

    BlokusMain blok = new BlokusMain();

    public static void main(String[] args) {

        String read = "";
        Model game = new Game();

        System.out.println(game.getCurrentPlayer().getStock().getAPiece(1).getPositions().get(0).toString());
        System.out.println("Bienvenue sur Blokus ! Choisissez une option :");
        System.out.println("Show : Affiche le board");
        System.out.println("Stock : Permet de consulter votre stock");
        System.out.println("Play n i j : Play suivi du n° de la piece et des "
                + "coordonnées (i,j) sur le board");
        System.out.println("Exit : Permet de quitter Blokus");
        Scanner scan = new Scanner(System.in);
        while (!read.equals("exit")) {
            try {
                System.out.println("Au tour de " + game.getCurrentPlayer().getName());
                read = scan.nextLine();
                read = checkScan(game, read);
            } catch (NumberFormatException ex) {
                System.out.println("You don't respect the types of characters ! ");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("you don't respect the instruction please enter the complete instruction");
            }
        }

    }

    /**
     * Allows to put the piece in the Board.
     *
     * @param game
     * @param read
     */
    private static void play(Model game, String read) {
        String[] split = read.split(" ");
        try {
            int n = Integer.parseInt(split[1]);
            int i = Integer.parseInt(split[2]);
            int j = Integer.parseInt(split[3]);
            if (game.currentPlayer().getStock().getAPiece(n) != null) {

                game.selectPiece(game.getCurrentPlayer().getStock().getAPiece(n), game.currentPlayer());
                game.playLap(new Point(i, j));
            }

        } catch (NumberFormatException e) {
            System.out.println("Erreur de données");
        }

    }

    /**
     * Allows to know what the user put in console.
     *
     * @param game
     * @param read
     * @return the word of the user put.
     */
    private static String checkScan(Model game, String read) {
        if (read.equals("Show") || read.contains("show")) {
            System.out.println(boardDisplay(game));

        } else if (read.equals("Stock") || read.contains("stock")) {
            System.out.println(game.currentPlayer().getStock().toString());
        } else if (read.equals("Play") || read.contains("play")) {
            play(game, read);
        } else if (read.equals("Exit") || read.contains("exit")) {
            read = "exit";
        } else if (read.equals("Score") || read.contains("score")) {
            System.out.println("score:" + game.currentPlayer().getScore());
        }
        return read;
    }

}
