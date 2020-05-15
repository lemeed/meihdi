package g49262.stratego.model;

import g49262.stratego.model.pieces.Bomb;
import g49262.stratego.model.pieces.Eclaireur;
import g49262.stratego.model.pieces.Marechal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class PieceTest {

    public PieceTest() {
    }

    @Test
    public void testGetColor() {
        Piece p = new Piece(0, PlayerColor.BLUE);
        assertEquals(PlayerColor.BLUE, p.getColor());
    }

    @Test
    public void testGetRank() {
        Piece p = new Piece(0, PlayerColor.BLUE);
        assertEquals(0, p.getRank());
    }

    @Test
    public void isStrongerWhenItsTrue() {
        Piece p = new Piece(0, PlayerColor.BLUE);
        Piece p2 = new Piece(9, PlayerColor.BLUE);

        assertTrue(p2.isStronger(p));
    }

    @Test
    public void isStrongerWhenItsFalse() {
        Piece p = new Piece(0, PlayerColor.BLUE);
        Piece p2 = new Piece(9, PlayerColor.BLUE);

        assertFalse(p.isStronger(p2));
    }

    @Test
    public void isStrongerWhenItsTheSame() {
        Piece p = new Piece(0, PlayerColor.BLUE);
        Piece p2 = new Piece(0, PlayerColor.BLUE);

        assertFalse(p.isStronger(p2));
    }

    @Test
    public void testHasSameRank() {
        Piece p = new Piece(0, PlayerColor.BLUE);
        Piece p2 = new Piece(0, PlayerColor.BLUE);

        assertTrue(p.hasSameRank(p2));
    }

    @Test
    public void testHasSameRankWhenItsFalse() {
        Piece p = new Piece(0, PlayerColor.BLUE);
        Piece p2 = new Piece(9, PlayerColor.BLUE);

        assertFalse(p.hasSameRank(p2));
    }

    @Test
    public void TestCanCrossWhenHeCan() {
        Game g = new Game();
        g.initialize();
        Square s = g.getBoard()[2][0];
        Piece p = new Piece(0, PlayerColor.BLUE);

        assertTrue(p.canCross(s));
    }

    @Test
    public void TestCanCrossWhenItsNotPossible() {
        Game g = new Game();
        g.initialize();
        Square s = g.getBoard()[2][1];
        Piece p = new Piece(0, PlayerColor.BLUE);

        assertFalse(p.canCross(s));
    }

    @Test
    public void TestNbStepsOfBomb() {
        Piece p = new Bomb(PlayerColor.BLUE);
        assertTrue(p.getNbSteps() == 0);
    }

    @Test
    public void TestNbStepsOfEclaireur() {
        Piece p = new Eclaireur(PlayerColor.BLUE);
        assertTrue(p.getNbSteps() == 2);
    }

    @Test
    public void TestNbStepsOfMarechal() {
        Piece p = new Marechal(PlayerColor.BLUE);
        assertTrue(p.getNbSteps() == 1);
    }

    @Test
    public void TestConstructorWithNbStep() {
        Piece p = new Piece(2, 1, PlayerColor.BLUE);
        assertTrue(p.getNbSteps() == 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestConstructorWithNegatifNbStep() {
        Piece p = new Piece(2, -3, PlayerColor.BLUE);

    }
}
