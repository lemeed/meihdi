package g49262.stratego.model;

import static g49262.stratego.model.PlayerColor.BLUE;
import static g49262.stratego.model.PlayerColor.RED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SquareTest {

    @Test
    public void testIsFreeTrue() {
        System.out.println("testIsFreeTrue");
        Square instance = new Square();
        boolean expResult = true;
        boolean result = instance.isFree();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsFreeFalse() {
        System.out.println("testIsFreeFalse");
        Square instance = new Square();
        instance.put(new Piece(4, PlayerColor.BLUE));
        boolean expResult = false;
        boolean result = instance.isFree();
        assertEquals(expResult, result);
    }

    @Test(expected = NullPointerException.class)
    public void testPutWhenPieceIsNull() {
        System.out.println("testPutWhenPieceIsNull");
        Piece piece = null;
        Square instance = new Square();
        instance.put(piece);
    }

    @Test
    public void testPutWhenSquareIsEmpty() {
        System.out.println("testPutWhenSquareIsEmpty");
        Piece piece = new Piece(8, PlayerColor.RED);
        Square instance = new Square();
        instance.put(piece);
        Piece expResult = instance.getPiece();
        assertEquals(expResult, piece);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetPieceWhenSquareFill() {
        System.out.println("testGetPieceWhenSquareFill");
        Piece piece1 = new Piece(8, PlayerColor.RED);
        Piece piece2 = new Piece(1, PlayerColor.BLUE);
        Square instance = new Square();
        instance.put(piece1);
        instance.put(piece2);
    }

    @Test
    public void testGetPieceWhenSquareEmpty() {
        System.out.println("testGetPieceWhenSquareEmpty");
        Square instance = new Square();
        Piece expResult = instance.getPiece();
        assertNull(expResult);
    }

    @Test
    public void equalsTrueMyself() {
        Square square1 = new Square();
        assertTrue(square1.equals(square1));
        assertTrue(square1.hashCode() == square1.hashCode());
    }

    @Test
    public void equalsTrueEmpty() {
        Square square1 = new Square();
        Square square2 = new Square();
        assertTrue(square1.equals(square2));
        assertTrue(square1.hashCode() == square2.hashCode());
    }

    @Test
    public void equalsTrueSameContent() {
        Square square1 = new Square();
        Square square2 = new Square();
        square1.put(new Piece(1, BLUE));
        square2.put(new Piece(1, BLUE));
        assertTrue(square1.equals(square2));
    }

    @Test
    public void equalsFalseDifferentContent() {
        Square square1 = new Square();
        Square square2 = new Square();
        square1.put(new Piece(1, BLUE));
        square2.put(new Piece(3, BLUE));
        assertFalse(square1.equals(square2));
    }

    @Test
    public void equalsFalseOneEmpty() {
        Square square1 = new Square();
        Square square2 = new Square();
        square1.put(new Piece(1, BLUE));
        assertFalse(square1.equals(square2));
    }

    @Test
    public void equalsFalseOtherObject() {
        Square square1 = new Square();
        String square2 = "abcd";
        assertFalse(square1.equals(square2));
    }

    @Test
    public void equalsFalseNull() {
        Square square1 = new Square();
        assertFalse(square1.equals(null));
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Square instance = new Square();
        String result = instance.toString();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testIsMyOwnWhenItsTrue() {
        Game g = new Game();
        g.initialize();
        Square instance = g.getBoard()[0][1];
        assertTrue(instance.isMyOwn(RED));
    }

    @Test
    public void testIsMyOwnWhenItsFalse() {
        Game g = new Game();
        g.initialize();
        Square instance = g.getBoard()[0][1];
        assertFalse(instance.isMyOwn(BLUE));
    }

    @Test
    public void testIsMyOwnWhenItsNull() {
        Game g = new Game();
        g.initialize();
        Square instance = g.getBoard()[0][0];
        assertFalse(instance.isMyOwn(RED));
    }

    @Test
    public void TestIsLandWhenIsTrue() {
        Game g = new Game();
        g.initialize();
        Square instance = g.getBoard()[0][0];

        assertTrue(instance.isLand());
    }

    @Test
    public void TestIsLandWhenIsFalse() {
        Game g = new Game();
        g.initialize();
        Square instance = g.getBoard()[2][1];

        assertFalse(instance.isLand());
    }

}
