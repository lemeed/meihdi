package g49262.stratego.model;

import static g49262.stratego.model.PlayerColor.BLUE;
import static g49262.stratego.model.PlayerColor.RED;
import g49262.stratego.model.pieces.Bomb;
import g49262.stratego.model.pieces.Eclaireur;
import g49262.stratego.model.pieces.Flag;
import g49262.stratego.model.pieces.General;
import g49262.stratego.model.pieces.Marechal;
import g49262.stratego.model.pieces.Miner;
import g49262.stratego.model.pieces.Spy;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private final Square[][] defaultBoard = {
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()},
        {new Square(), new Square(), new Square(), new Square(), new Square(), new Square()}};

    @Before
    public void setUp() throws Exception {
        Piece p1 = new Flag(RED);
        Piece p2 = new General(RED);
        Piece p3 = new Flag(BLUE);
        Piece p4 = new General(BLUE);
        Piece piece3 = new Bomb(RED);
        Piece piece4 = new Miner(RED);
        Piece piece5 = new Flag(BLUE);
        Piece piece6 = new General(BLUE);
        Piece piece7 = new Bomb(BLUE);
        Piece piece8 = new Miner(BLUE);
        Piece piece9 = new Eclaireur(BLUE);
        Piece piece10 = new Eclaireur(RED);
        Piece piece11 = new Marechal(BLUE);
        Piece piece12 = new Marechal(RED);
        Piece piece13 = new Spy(BLUE);
        Piece piece14 = new Spy(RED);
        defaultBoard[0][1].put(p1);
        defaultBoard[3][2].put(p2);
        defaultBoard[4][2].put(p3);
        defaultBoard[4][1].put(p4);
        defaultBoard[1][0].put(piece3);
        defaultBoard[1][2].put(piece4);
        defaultBoard[3][1].put(piece7);
        defaultBoard[2][0].put(piece8);
        defaultBoard[5][4].put(piece9);
        defaultBoard[0][5].put(piece10);
        defaultBoard[6][0].put(piece11);
        defaultBoard[6][1].put(piece14);
        defaultBoard[5][3].put(piece13);
        defaultBoard[3][4].put(piece12);

    }

    @Test
    public void testInitialize() {
        System.out.println("initialize");
        Game instance = new Game();
        instance.initialize();
        Square[][] result = instance.getBoard();
        assertArrayEquals(defaultBoard, result);
    }

    @Test(expected = IllegalStateException.class)
    public void testStartWhenNoBoard() {
        System.out.println("testStartWhenNoBoard");
        Game instance = new Game();
        instance.start();
    }

    @Test
    public void testGetBoardWhenGameBegin() {
        System.out.println("testGetBoardWhenGameBegin");
        Game instance = new Game();
        instance.initialize();
        Square[][] expResult = defaultBoard;
        Square[][] result = instance.getBoard();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testSelect() {
        System.out.println("testSelect");
        Game g = new Game();
        g.initialize();

        g.select(0, 1);
        Piece result = g.getSelected();
        Piece test = defaultBoard[0][1].getPiece();

        assertEquals(result, test);

    }

    @Test(expected = IllegalStateException.class)
    public void testSelectWhenItsnull() {
        System.out.println("testSelectWhenItsNull");
        Game g = new Game();
        g.initialize();

        g.select(1, 1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testSelectWhenThePositionIsNotGood() {
        System.out.println("testSelectWhenThePositionIsNotGood");
        Game g = new Game();
        g.initialize();

        g.select(-1, 1);

    }

    @Test(expected = IllegalStateException.class)
    public void testSelectWhenIsNotTheGoodPlayer() {
        System.out.println("testSelectWhenIsNotTheGoodPlayer");
        Game g = new Game();
        g.initialize();

        g.select(4, 2);

    }

    @Test
    public void testGetSelected() {
        System.out.println("testgetSelected");
        Game g = new Game();
        g.initialize();

        g.select(0, 1);

        Piece test = defaultBoard[0][1].getPiece();
        Piece result = g.getSelected();

        assertEquals(test, result);

    }

    @Test(expected = NullPointerException.class)
    public void testGetSelectedWhenTheSquareIsNull() {
        System.out.println("testgetSelected");
        Game g = new Game();
        g.initialize();

        g.getSelected();

    }

    @Test
    public void testGetMovesForTheFlagRed() {

        Game game = new Game();
        game.initialize();
        game.select(6, 1);
        List<Move> l = game.getMoves();

        assertEquals(l.size(), 3);
    }

    @Test
    public void testApplyRemoveBoard() {
        Game g = new Game();
        g.initialize();
        g.select(6, 1);
        List<Move> l = g.getMoves();
        g.apply(l.get(0));
        Square s = new Square();
        assertEquals(g.getBoard()[6][1], s);
    }

    @Test
    public void testApplyWhenTheEndIsFree() {
        Game g = new Game();
        g.initialize();
        g.select(6, 1);
        List<Move> l = g.getMoves();
        g.apply(l.get(0));
        assertEquals(g.getBoard()[5][1].getPiece().getRank(), 1);
    }

    @Test
    public void testIfItsSwap() {
        Game g = new Game();
        g.initialize();
        g.select(6, 1);
        List<Move> l = g.getMoves();
        g.apply(l.get(0));
        assertEquals(g.current.color, BLUE);
    }

    @Test
    public void TestGetCurrent() {
        Game g = new Game();
        g.initialize();
        assertEquals(RED, g.getCurrent().color);
    }

    @Test
    public void testGetMovesWhenTheyAreAWater() {
        Game g = new Game();
        g.initialize();
        g.select(1, 2);
        List<Move> l = g.getMoves();

        assertEquals(l.size(), 3);
    }

    @Test
    public void testGetMovesWhenNbStepsEqualsTwo() {
        Game g = new Game();
        g.initialize();
        g.select(0, 5);
        List<Move> l = g.getMoves();
        assertEquals(l.size(), 4);
    }

    @Test
    public void testFightWhenSpyAttackMarechal() {
        Game g = new Game();
        g.initialize();
        g.select(6, 1);
        List<Move> l = g.getMoves();
        g.apply(l.get(1));
        assertEquals(g.getBoard()[6][0].getPiece().getRank(), 1);
    }

    @Test
    public void TestFightWhenMarechalAttackSpy() {
        Game g = new Game();
        g.initialize();
        g.select(0, 5);
        List<Move> l = g.getMoves();
        g.apply(l.get(0));
        g.select(6, 0);
        l = g.getMoves();
        g.apply(l.get(1));
        assertEquals(g.getBoard()[6][1].getPiece().getRank(), 1);

    }

    @Test
    public void TestFightLowestRankAttack() {
        Game g = new Game();
        g.initialize();
        g.select(0, 5);
        List<Move> l = g.getMoves();
        g.apply(l.get(0));
        g.select(5, 4);
        l = g.getMoves();
        g.apply(l.get(3));
        assertEquals(g.getBoard()[3][4].getPiece().getRank(), 10);
    }
    
    @Test
    public void TestGetWinnersBluePlayerWon(){
        Game g = new Game();
        g.initialize();
        g.getCurrent().removePiece(new Flag(RED));
        
        assertEquals(g.getWinners().get(0).color,BLUE);
    }
    
    @Test
    public void TestGetWinnersRedPlayerWon(){
        Game g = new Game();
        g.initialize();
        g.select(1,2);
        List<Move> m = g.getMoves();
        g.apply(m.get(0));
        g.getCurrent().removePiece(new Flag(BLUE));
        
        assertEquals(g.getWinners().get(0).color,RED);
    }
    
    @Test
    public void TestGetWinnersSizeOfList(){
        Game g = new Game();
        g.initialize();
        g.getCurrent().removePiece(new Flag(RED));
        
        assertEquals(g.getWinners().size() , 1);
    }
    
    @Test
    public void TestGetWinnersWhenDraw(){
        Game g = new Game();
        g.initialize();
        g.getCurrent().removePiece(new Flag(RED));
        g.select(1,2);
        List<Move> m = g.getMoves();
        g.apply(m.get(0));
        g.getCurrent().removePiece(new Flag(BLUE));
        
        assertEquals(g.getWinners().size() , 2);
        
        
    }
}