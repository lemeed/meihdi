package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;

/**
 * This class allows to define a piece like a Spy.
 *
 * @author Meihdi El Amouri
 */
public class Spy extends Piece {

    /**
     * It's the constructor of the Miner
     * @param aColor the color of the player.
     */
    public Spy(PlayerColor aColor) {
        super(1, aColor);
    }

    /**
     * Allows to know if the piece in parameter is stronger.
     * @param other the other piece.
     * @return true if the piece is stronger that the parameter piece.
     */
    @Override
    public boolean isStronger(Piece other) {
        return other.getRank() == 10 || super.isStronger(other);
    }
}
