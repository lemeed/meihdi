/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.viewFx;

import blokus.model.Model;
import blokus.model.Piece;
import blokus.model.Player;
import blokus.model.Point;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * The piece grid of list piece .
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class PieceGrid extends GridPane {

    private Piece piece;
    private Player player;

    /**
     * The constructor of PieceGrid.
     *
     * @param piece
     * @param g
     * @param player
     */
    public PieceGrid(Piece piece, Model g, Player player) {
        super();
        this.piece = piece;
        this.player = player;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                addSquare(i, j);
            }
            this.setPadding(new Insets(1));

        }
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {

            if (g.getCurrentPlayer().equals(player)) {
                g.selectPiece(piece, player);
            }
            event.consume();
        });

    }

    /**
     * Constructor of an empty piece (already played)
     */
    public PieceGrid() {
        super();

    }

    /**
     * Allows to put color piece on the piece grid.
     *
     * @param x
     * @param y
     */
    private void addSquare(int x, int y) {
        Rectangle r = new Rectangle(8, 8);

        if (piece.getPositions().contains(new Point(x, y))) {
            r.setFill(player.getColor());

        } else {

            r.setFill(Color.WHITE);
        }
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(1);
        this.add(r, x, y);
    }

}
