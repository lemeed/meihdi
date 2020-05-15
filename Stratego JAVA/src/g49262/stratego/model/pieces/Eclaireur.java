package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;

/**
 * This class allows to define a piece like a Eclaireur.
 * @author Meihdi El Amouri 
 */
public class Eclaireur extends Piece {
    
    /**
     * It's the constructor of the Eclaireur.
     * @param aColor the color of the player.
     */
    public Eclaireur(PlayerColor aColor) {
        super(2,2, aColor);
    }
    
 }
