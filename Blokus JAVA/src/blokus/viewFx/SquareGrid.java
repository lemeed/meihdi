/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.BlokusException;
import blokus.model.Model;
import blokus.model.Square;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The Square grid of the board.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class SquareGrid extends Rectangle {

    private final int x;
    private final int y;
    

    /**
     * the constructor of SquareGrid.
     *
     * @param x
     * @param y
     * @param g
     */
    public SquareGrid(int x, int y, Model g) {
        super(27, 27);
        this.x = x;
        this.y = y;
        this.setStroke(Color.TRANSPARENT);
        //this.setStrokeWidth(1);
        addColor(g);
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, new ClickedAction(g, this));

        this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EnteredAction(g, this));

    }

    /**
     * Allows to add color if they are a piece on the board.
     *
     * @param game
     */
    public final void addColor(Model game) {
        if (game.getBlokusBoard()[x][y].equals(new Square())) {
            this.setFill(Color.BLACK);
        } else if (game.getBlokusBoard()[x][y].getPlayer() != null) {
            this.setFill(game.getBlokusBoard()[x][y].getPlayer().getColor());
        }

    }

    /**
     * Color the piece with the player color in the board before put it in
     *
     * @param g
     * @throws BlokusException
     */
    public void colorSquare(Model g) throws BlokusException {
        if (g.verifyMove(g.getCurrentPos())) {
            this.setFill(g.getCurrentPlayer().getColor());
        } else {
            this.setFill(Color.GRAY);
        }
    }

    /**
     * Get the X of the square
     *
     * @return the abscisce coordinates
     */
    public int getXforSquare() {
        return this.x;
    }

    /**
     * Get the Y of the Square
     *
     * @return the ordinate coordinates
     */
    public int getYforSquare() {
        return this.y;
    }

}
