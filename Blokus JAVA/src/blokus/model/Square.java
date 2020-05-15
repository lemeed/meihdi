/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.model;

import java.util.Objects;

/**
 * The Square in the board.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Square {

    private Piece piece;
    private Player player;

    /**
     * the constructor of a empty Square.
     */
    public Square() {
        this.piece = new Piece();
        this.player = null;
    }

    /**
     * The constructor of a square.
     *
     * @param piece
     * @param player
     */
    public Square(Piece piece, Player player) {
        this.piece = piece;
        this.player = player;
    }

    /**
     * the getter of piece.
     *
     * @return piece.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * the getter of player.
     *
     * @return player.
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Square other = (Square) obj;
        if (!Objects.equals(this.piece, other.piece)) {
            return false;
        }
        return Objects.equals(this.player, other.player);
    }

}
