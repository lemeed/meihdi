package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class BombTest {

    public BombTest() {
    }

    @Test
    public void testGetRankBomb() {
        Piece p = new Bomb(PlayerColor.RED);
        assertEquals(11, p.getRank());

    }

    @Test
    public void testCreateBombColor() {
        Piece p = new Bomb(PlayerColor.RED);
        assertEquals(PlayerColor.RED, p.getColor());

    }

    @Test
    public void testNbStepOfBomb() {
        Piece p = new Bomb(PlayerColor.BLUE);
        assertEquals(p.getNbSteps(), 0);
    }

}
