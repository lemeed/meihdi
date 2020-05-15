package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class MarechalTest {

    @Test
    public void testGetRankMarechal() {
        Piece p = new Marechal(PlayerColor.RED);
        assertEquals(10, p.getRank());

    }

    @Test
    public void testCreateMarechalColor() {
        Piece p = new Marechal(PlayerColor.RED);
        assertEquals(PlayerColor.RED, p.getColor());

    }

    @Test
    public void testNbStepOfMarechal() {
        Piece p = new Marechal(PlayerColor.BLUE);
        assertEquals(p.getNbSteps(), 1);
    }

    @Test
    public void testIsStrongerWhenMarechalMeetSpy() {
        Piece mar = new Marechal(PlayerColor.BLUE);
        Piece spy = new Spy(PlayerColor.RED);

        assertFalse(mar.isStronger(spy));
    }

}
