package g49262.stratego.model;

import g49262.stratego.model.pieces.Flag;
import g49262.stratego.model.pieces.General;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PlayerTest {

    @Test(expected = NullPointerException.class)
    public void testConstructPlayerColorIsNull() {
        System.out.println("testConstructPlayerColorIsNull");
        new Player(null);
    }

    @Test
    public void testGetPiecesWhenStockEmpty() {
        System.out.println("testGetPiecesWhenStockEmpty");
        Player instance = new Player(PlayerColor.BLUE);
        List<Piece> result = instance.getPieces();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetPiecesWhenStockFill() {
        System.out.println("testGetPiecesWhenStockFill");
        Player instance = new Player(PlayerColor.BLUE);
        instance.addPiece(new Piece(0, PlayerColor.BLUE));
        instance.addPiece(new Piece(0, PlayerColor.BLUE));
        List<Piece> result = instance.getPieces();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testAddPiece() {
        System.out.println("addPiece");
        Player instance = new Player(PlayerColor.BLUE);
        instance.addPiece(new Piece(0, PlayerColor.BLUE));
        instance.addPiece(new Piece(0, PlayerColor.BLUE));
        List<Piece> expResult = new ArrayList<>();
        expResult.add(new Piece(0, PlayerColor.BLUE));
        expResult.add(new Piece(0, PlayerColor.BLUE));
        List<Piece> result = instance.getPieces();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetColorBlue() {
        System.out.println("testGetColorBlue");
        Player instance = new Player(PlayerColor.BLUE);
        PlayerColor expResult = PlayerColor.BLUE;
        PlayerColor result = instance.getColor();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetColorRed() {
        System.out.println("testGetColorRed");
        Player instance = new Player(PlayerColor.RED);
        PlayerColor expResult = PlayerColor.RED;
        PlayerColor result = instance.getColor();
        assertEquals(expResult, result);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Player instance = new Player(PlayerColor.BLUE);
        String result = instance.toString();
        assertFalse(result.isEmpty());
    }

    @Test
    public void equalsTrueMySelf() {
        Player player1 = new Player(PlayerColor.BLUE);
        assertTrue(player1.equals(player1));
        assertTrue(player1.hashCode() == player1.hashCode());
    }

    @Test
    public void equalsTrueNoPiece() {
        Player player1 = new Player(PlayerColor.BLUE);
        Player player2 = new Player(PlayerColor.BLUE);
        assertTrue(player1.equals(player2));
        assertTrue(player1.hashCode() == player2.hashCode());
    }

    @Test
    public void equalsTrueWithPiece() {
        Player player1 = new Player(PlayerColor.BLUE);
        Player player2 = new Player(PlayerColor.BLUE);
        player1.addPiece(new Piece(1, PlayerColor.BLUE));
        player2.addPiece(new Piece(1, PlayerColor.BLUE));
        assertTrue(player1.equals(player2));
        assertTrue(player1.hashCode() == player2.hashCode());
    }

    @Test
    public void equalsFalseDifferentColor() {
        Player player1 = new Player(PlayerColor.BLUE);
        Player player2 = new Player(PlayerColor.RED);
        assertFalse(player1.equals(player2));
    }

    @Test
    public void equalsFalseDifferentPiece() {
        Player player1 = new Player(PlayerColor.BLUE);
        Player player2 = new Player(PlayerColor.BLUE);
        player1.addPiece(new Piece(1, PlayerColor.BLUE));
        player2.addPiece(new Piece(2, PlayerColor.BLUE));
        assertFalse(player1.equals(player2));
    }

    @Test
    public void equalsFalseOtherObject() {
        Player player1 = new Player(PlayerColor.BLUE);
        String player2 = "abcd";
        assertFalse(player1.equals(player2));
    }

    @Test
    public void equalsFalseNull() {
        Player player1 = new Player(PlayerColor.BLUE);
        assertFalse(player1.equals(null));
    }

    @Test
    public void removePieceFlag() {
        Game g = new Game();
        g.initialize();
        Piece p = new Flag(PlayerColor.RED);
        Player play = g.getCurrent();
        play.removePiece(p);

        assertEquals(play.getPieces().get(0).getRank(), 9);

    }

    @Test
    public void removePieceGeneral() {
        Game g = new Game();
        g.initialize();
        Piece p = new General(PlayerColor.RED);
        Player play = g.getCurrent();
        play.removePiece(p);

        assertEquals(play.getPieces().get(0).getRank(), 0);

    }

    @Test
    public void testHasFlag() {
        Game g = new Game();
        g.initialize();
        Player play = g.getCurrent();
        assertTrue(play.hasFlag());
    }

    @Test
    public void testHasFlagButNot() {
        Game g = new Game();
        g.initialize();
        Player play = g.getCurrent();
        Piece p = new Flag(PlayerColor.RED);
        play.removePiece(p);
        assertFalse(play.hasFlag());
    }

    @Test
    public void testHasFlagButNotOpponent() {
        Game g = new Game();
        g.initialize();
        Player play = g.opponent;
        Piece p = new Flag(PlayerColor.BLUE);
        play.removePiece(p);
        assertFalse(play.hasFlag());
    }
}
