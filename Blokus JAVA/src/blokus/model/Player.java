/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.model;

import javafx.scene.paint.Color;

/**
 * The player in game.
 *
 * @author g49262
 */
public class Player {

    private String pseudonym;
    private Color color;
    private int score;
    private Stock stock;
    private int idPlayer;
    private Point start;
    private boolean bot;

    /**
     * the constructor of player.
     *
     * @param aPseudonym
     * @param aColor
     */
    public Player(String aPseudonym, Color color, int idPlayer, Point aStart) {

        this.pseudonym = aPseudonym;
        this.color = color;
        this.score = 0;
        this.stock = new Stock();
        this.idPlayer = idPlayer;
        this.start = aStart;
        this.bot = false;
        stock.createPieces();

    }

    /**
     * the getter of name.
     *
     * @return the name of player.
     */
    public String getName() {
        return pseudonym;
    }

    /**
     * the getter of color.
     *
     * @return color the color of player.
     */
    public Color getColor() {
        return color;
    }

    /**
     * the getter of score.
     *
     * @return the score of player.
     */
    public int getScore() {
        return score;
    }

    /**
     * the setter of score.
     *
     * @param aScore
     */
    public void setScore(int aScore) {
        score += aScore;
    }

    /**
     * the setter of color.
     *
     * @param color
     */
    void setColor(Color color) {
        color = color;
    }

    /**
     * the getter of stock.
     *
     * @return
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * the getter of the idPlayer.
     *
     * @return the id of player.
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    public Point getStart() {
        return start;
    }

    /**
     * Allows to know if the player is a bot or not.
     *
     * @return
     */
    boolean isBot() {
        return bot;
    }

}
