package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;

/**
 * A flag piece
 *
 * @author Meihdi El Amouri
 */
public class Flag extends Piece {

    /**
     * The constructor of Flag.
     *
     * @param aColor The color of the piece.
     */
    public Flag(PlayerColor aColor) {
        super(0, 0, aColor);
    }

}
