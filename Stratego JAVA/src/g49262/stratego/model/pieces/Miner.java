package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;

/**
 * This class allows to define a piece like a Miner.
 *
 * @author Meihdi El Amouri
 */
public class Miner extends Piece {

    /**
     * It's the constructor of the Miner
     * @param aColor the color of the player.
     */
    public Miner(PlayerColor aColor) {
        super(3, aColor);
    }

    /**
     * Allows to know if the piece in parameter is stronger.
     * @param other the other piece.
     * @return true if the piece is stronger that the parameter piece.
     */
    @Override
    public boolean isStronger(Piece other) {
        return other.getRank() == 11 || super.isStronger(other);
    }

}
