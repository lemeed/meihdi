package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;
import g49262.stratego.model.pieces.Flag;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class FlagTest {

    public FlagTest() {
    }

    @Test
    public void testGetRankFlag() {
        Piece p = new Flag(PlayerColor.RED);
        assertEquals(0, p.getRank());

    }

    @Test
    public void testCreateFlagColor() {
        Piece p = new Flag(PlayerColor.RED);
        assertEquals(PlayerColor.RED, p.getColor());

    }

    @Test
    public void testNbStepOfFlag() {
        Piece p = new Flag(PlayerColor.BLUE);
        assertEquals(p.getNbSteps(), 0);
    }

}
