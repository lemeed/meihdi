package g49262.stratego.model;

import g49262.stratego.model.pieces.Flag;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class MoveTest {
    
    @Test
    public void TestGetPiece(){
        Move m = new Move(new Flag(PlayerColor.RED), new Position(0, 0),new Position(1, 0));
        assertEquals(m.getPiece(),new Flag(PlayerColor.RED));
    }
    
    @Test
    public void TestGetStart(){
        Move m = new Move(new Flag(PlayerColor.RED), new Position(0, 0),new Position(1, 0));
        assertEquals(m.getStart(),new Position(0, 0));
    }
    
    @Test
    public void TestGetEnd(){
        Move m = new Move(new Flag(PlayerColor.RED), new Position(0, 0),new Position(1, 0));
        assertEquals(m.getEnd(),new Position(1, 0));
    }
    
    @Test(expected = NullPointerException.class)
    public void TestMoveWhenThePieceIsNull(){
        Move m = new Move(null , new Position(0, 0),new Position(1, 0));
    }
    
    @Test(expected = NullPointerException.class)
    public void TestMoveWhenThePosStartIsNull(){
        Move m = new Move(new Flag(PlayerColor.BLUE),null,new Position(1, 0));
    }
    
    @Test(expected = NullPointerException.class)
    public void TestMoveWhenThePosEndIsNull(){
        Move m = new Move(new Flag(PlayerColor.BLUE),new Position(1, 0),null);
    }
}
