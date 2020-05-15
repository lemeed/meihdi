package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;

/**
 * This class allows to define a piece like a Marechal.
 *
 * @author Meihdi El Amouri
 */
public class Marechal extends Piece {

    /**
     * It's the constructor of the Marechal
     * @param aColor the color of the player.
     */
    public Marechal(PlayerColor aColor) {
        super(10, aColor);
    }

    /**
     * Allows to know if the piece in parameter is stronger.
     * @param other the other piece.
     * @return true if the piece is stronger that the parameter piece.
     */
    @Override
    public boolean isStronger(Piece other) {
        if (other.getRank() == 1){
            return false;
        }
        return super.isStronger(other);
    }
}
