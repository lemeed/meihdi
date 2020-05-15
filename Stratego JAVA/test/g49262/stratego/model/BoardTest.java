package g49262.stratego.model;

import g49262.stratego.model.*;
import g49262.stratego.model.pieces.Flag;
import g49262.stratego.model.pieces.General;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BoardTest {

    private final Square[][] defaultBoard = {
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()}};

    @Test
    public void testGetSquareWhenSquareIsFill() {
        System.out.println("testGetSquareWhenSquareIsFill");
        Position position = new Position(3, 2);
        Board instance = new Board();
        instance.put(new Piece(4, PlayerColor.BLUE), position);
        Square expResult = new Square();
        expResult.put(new Piece(4, PlayerColor.BLUE));
        Square result = instance.getSquare(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetSquareWhenSquareIsEmpty() {
        System.out.println("testGetSquareWhenSquareIsEmpty");
        Position position = new Position(0, 1);
        Board instance = new Board();
        Square expResult = new Square();
        Square result = instance.getSquare(position);
        assertEquals(expResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSquareWhenPositionOutside() {
        System.out.println("testGetSquareWhenPositionOutside");
        Position position = new Position(-1, 2);
        Board instance = new Board();
        instance.getSquare(position);
    }

    @Test
    public void testGetSquaresWhenDefaultBoard() {
        System.out.println("testGetSquaresWhenDefaultBoard");
        Board instance = new Board();
        Square[][] expResult = defaultBoard;
        Square[][] result = instance.getSquares();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testGetSquaresWhen2PiecesAdded() {
        System.out.println("testGetSquaresWhen2PiecesAdded");
        Board instance = new Board();
        instance.put(new Piece(4, PlayerColor.RED), new Position(1, 2));
        instance.put(new Piece(1, PlayerColor.BLUE), new Position(4, 3));
        Square[][] expResult = defaultBoard;
        defaultBoard[1][2].put(new Piece(4, PlayerColor.RED));
        defaultBoard[4][3].put(new Piece(1, PlayerColor.BLUE));
        Square[][] result = instance.getSquares();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testPutOnePiece() {
        System.out.println("testPutOnePiece");
        Piece piece = new Piece(2, PlayerColor.BLUE);
        Position position = new Position(0, 0);
        Board instance = new Board();
        instance.put(piece, position);
        Square expResult = new Square();
        expResult.put(piece);
        Square result = instance.getSquare(position);
        assertEquals(expResult, result);
    }

    @Test(expected = NullPointerException.class)
    public void testPutWhenPieceIsNull() {
        System.out.println("testPutWhenPieceIsNull");
        Piece piece = null;
        Position position = new Position(2, 2);
        Board instance = new Board();
        instance.put(piece, position);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPuWhenPositionIsOutside() {
        System.out.println("testPuWhenPositionIsOutside");
        Piece piece = new Piece(1, PlayerColor.BLUE);
        Position position = new Position(9, 2);
        Board instance = new Board();
        instance.put(piece, position);
    }

    @Test
    public void testIsInsideWhenCornerUpLeft() {
        System.out.println("testIsInsideWhenCornerUpLeft");
        Position position = new Position(0, 0);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenCornerUpRight() {
        System.out.println("testIsInsideWhenCornerUpRight");
        Position position = new Position(0, 3);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenCornerDownleft() {
        System.out.println("testIsInsideWhenCornerDownleft");
        Position position = new Position(4, 0);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenCornerDownRight() {
        System.out.println("testIsInsideWhenCornerDownRight");
        Position position = new Position(4, 3);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenBoundaryUp() {
        System.out.println("testIsInsideWhenBoundaryUp");
        Position position = new Position(0, 1);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenBoundaryDown() {
        System.out.println("testIsInsideWhenBoundaryDown");
        Position position = new Position(4, 2);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenBoundaryLeft() {
        System.out.println("testIsInsideWhenBoundaryLeft");
        Position position = new Position(1, 0);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenBoundaryRight() {
        System.out.println("testIsInsideWhenBoundaryRight");
        Position position = new Position(3, 3);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenOutsideBoundaryUp() {
        System.out.println("testIsInsideWhenOutsideBoundaryUp");
        Position position = new Position(-1, 1);
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenOutsideBoundaryDown() {
        System.out.println("testIsInsideWhenOutsideBoundaryDown");
        Position position = new Position(9, 2);
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenOutsideBoundaryLeft() {
        System.out.println("testIsInsideWhenOutsideBoundaryLeft");
        Position position = new Position(1, -1);
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenOutsideBoundaryRight() {
        System.out.println("testIsInsideWhenOutsideBoundaryRight");
        Position position = new Position(8, 9);
        Board instance = new Board();
        boolean expResult = false;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsInsideWhenInside() {
        System.out.println("testIsInsideWhenInside");
        Position position = new Position(2, 1);
        Board instance = new Board();
        boolean expResult = true;
        boolean result = instance.isInside(position);
        assertEquals(expResult, result);
    }

    @Test(expected = NullPointerException.class)
    public void testIsInsideWhenPositionIsNull() {
        System.out.println("testIsInsideWhenPositionIsNull");
        Board instance = new Board();
        instance.isInside(null);

    }

    @Test
    public void testIsFreeWhenIsFree() {
        System.out.println("testIsFreeWhenIsFree");

        Board instance = new Board();

        Position p = new Position(0, 0);
        boolean expResult = true;
        boolean result = instance.isFree(p);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsFreeWhenIsNotFree() {
        Board b = new Board();
        Piece p = new Flag(PlayerColor.BLUE);
        Position po = new Position(0, 1);
        b.put(p, po);
        Square s = b.getSquare(po);
        boolean test = false;
        boolean result = b.isFree(po);
        assertEquals(test, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsFreeWhenPositionIsNotInside() {
        System.out.println("testIsFreeWhenPositionIsNotInside");
        Board b = new Board();
        Position p = new Position(-1, 1);
        b.isFree(p);
    }

    @Test
    public void IsMyOwnWhenItsTrue() {
        System.out.println("IsMyOwnWhenItsTrue");
        Board b = new Board();
        Piece p = new Flag(PlayerColor.BLUE);
        Position po = new Position(0, 1);
        b.put(p, po);
        boolean result = b.isMyOwn(po, PlayerColor.BLUE);
        boolean test = true;
        assertEquals(result, test);
    }

    @Test
    public void IsMyOwnWhenItsFalse() {
        System.out.println("IsMyOwnWhenItsFalse");
        Board b = new Board();
        Piece p = new Flag(PlayerColor.BLUE);
        Position po = new Position(0, 1);
        b.put(p, po);
        boolean result = b.isMyOwn(po, PlayerColor.RED);
        boolean test = false;
        assertEquals(result, test);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsMyOwnWhenThePositionisNotInside() {
        System.out.println("testIsMyOwnWhenPositionIsNotInside");
        Board b = new Board();
        Position p = new Position(-1, 1);
        b.isMyOwn(p, PlayerColor.BLUE);
    }

    @Test
    public void testGetPiece() {
        System.out.println("testGetPiece");
        Board b = new Board();
        Piece piece = new Piece(0, PlayerColor.BLUE);
        Position pos = new Position(0, 0);
        b.put(piece, pos);
        assertEquals(b.getPiece(pos), piece);
    }

    @Test
    public void testGetPieceWhenItsnull() {
        System.out.println("testGetPieceWhenItsNull");
        Board b = new Board();

        Position pos = new Position(0, 0);

        assertEquals(b.getPiece(pos), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPieceWhenPositionIsNotInside() {
        System.out.println("testGetPieceWhenPositionIsNotInside");
        Board b = new Board();
        Position p = new Position(-1, 1);
        b.getPiece(p);
    }

    @Test
    public void testRemoveWhenTheSquareIsNotFree() {
        System.out.println("testRemoveWhenTheSquareIsNotFree");
        Board b = new Board();
        Piece p = new Flag(PlayerColor.BLUE);
        Position pos = new Position(0, 0);
        b.put(p, pos);
        b.remove(pos);

        assertEquals(b.getPiece(pos), null);
    }

    @Test
    public void testGetTakenSquareWhenOnePiece() {
        Board b = new Board();
        Piece p = new Flag(PlayerColor.BLUE);
        Player play = new Player(PlayerColor.BLUE);
        Position pos = new Position(0, 0);
        b.put(p, pos);
        List<Position> list = b.getTakenSquare(play);

        assertEquals(list.size(), 1);
    }

    @Test
    public void testGetTakenSquareWhenTwoPiece() {
        Board b = new Board();
        Piece p = new Flag(PlayerColor.BLUE);
        Piece p2 = new General(PlayerColor.BLUE);
        Player play = new Player(PlayerColor.BLUE);
        Position pos = new Position(0, 0);
        Position pos1 = new Position(1, 1);
        b.put(p, pos);
        b.put(p2, pos1);
        List<Position> list = b.getTakenSquare(play);

        assertEquals(list.size(), 2);
    }

    @Test
    public void testGetTakenSquareWhen0Piece() {
        Board b = new Board();

        Player play = new Player(PlayerColor.BLUE);

        List<Position> list = b.getTakenSquare(play);

        assertEquals(list.size(), 0);
    }

}
