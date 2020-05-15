package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;

/**
 * The general piece.
 *
 * @author Meihdi El Amouri
 */
public class General extends Piece {

    /**
     * The constructor of General piece.
     *
     * @param aColor the color of player.
     */
    public General(PlayerColor aColor) {
        super(9, aColor);
    }

}
