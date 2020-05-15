package g49262.stratego.model.pieces;

import g49262.stratego.model.Piece;
import g49262.stratego.model.PlayerColor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class MinerTest {

    @Test
    public void testGetRankMiner() {
        Piece p = new Miner(PlayerColor.RED);
        assertEquals(3, p.getRank());

    }

    @Test
    public void testCreateMinerColor() {
        Piece p = new Miner(PlayerColor.RED);
        assertEquals(PlayerColor.RED, p.getColor());

    }

    @Test
    public void testNbStepOfMiner() {
        Piece p = new Miner(PlayerColor.BLUE);
        assertEquals(p.getNbSteps(), 1);
    }

    @Test
    public void testIsStrongerWhenMinerMeetBomb() {
        Piece miner = new Miner(PlayerColor.BLUE);
        Piece bomb = new Bomb(PlayerColor.RED);
        assertTrue(miner.isStronger(bomb));
    }

}
