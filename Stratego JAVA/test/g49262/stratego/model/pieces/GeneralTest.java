package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;
import g49262.stratego.model.pieces.General;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class GeneralTest {

    public GeneralTest() {
    }

    @Test
    public void testGetRankGeneral() {
        Piece p = new General(PlayerColor.RED);
        assertEquals(9, p.getRank());

    }

    @Test
    public void testCreateGeneralColor() {
        Piece p = new General(PlayerColor.RED);
        assertEquals(PlayerColor.RED, p.getColor());

    }

    @Test
    public void testNbStepOfGeneral() {
        Piece p = new General(PlayerColor.BLUE);
        assertEquals(p.getNbSteps(), 1);
    }
}
