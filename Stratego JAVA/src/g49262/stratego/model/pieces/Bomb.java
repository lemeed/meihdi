package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;

/**
 * This class allows to define a piece like a Bomb.
 *
 * @author Meihdi El Amouri
 */
public class Bomb extends Piece {

    /**
     * It's the constructor of the bomb
     * @param aColor the color of the player.
     */
    public Bomb(PlayerColor aColor) {
        super(11, 0, aColor);
    }

    
}
