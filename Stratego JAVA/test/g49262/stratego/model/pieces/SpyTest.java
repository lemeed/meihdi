package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class SpyTest {

    @Test
    public void testGetRankSpy() {
        Piece p = new Spy(PlayerColor.RED);
        assertEquals(1, p.getRank());

    }

    @Test
    public void testCreateSpyColor() {
        Piece p = new Spy(PlayerColor.RED);
        assertEquals(PlayerColor.RED, p.getColor());

    }

    @Test
    public void testNbStepOfSpy() {
        Piece p = new Spy(PlayerColor.BLUE);
        assertEquals(p.getNbSteps(), 1);
    }

    @Test
    public void testIsStrongerWhenSpyMeetMarechal() {
        Piece mar = new Marechal(PlayerColor.BLUE);
        Piece spy = new Spy(PlayerColor.RED);

        assertTrue(spy.isStronger(mar));
    }
}
