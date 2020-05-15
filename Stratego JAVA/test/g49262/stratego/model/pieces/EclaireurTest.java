package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class EclaireurTest {

    @Test
    public void testGetRankEclaireur() {
        Piece p = new Eclaireur(PlayerColor.RED);
        assertEquals(2, p.getRank());

    }

    @Test
    public void testCreateEclaireurColor() {
        Piece p = new Eclaireur(PlayerColor.RED);
        assertEquals(PlayerColor.RED, p.getColor());

    }

    @Test
    public void testNbStepOfEclaireur() {
        Piece p = new Eclaireur(PlayerColor.BLUE);
        assertEquals(p.getNbSteps(), 2);
    }

}
