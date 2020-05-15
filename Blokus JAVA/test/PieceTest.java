

import blokus.model.Piece;
import blokus.model.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri
 */
public class PieceTest {
    
    public PieceTest() {
    }

    /**
     * Test of getIdPiece method, of class Piece.
     */
    @Test
    public void testGetIdPiece() {
        System.out.println("getIdPiece");
        Piece instance = new Piece(1, new Point (1,1));
        int expResult = 1 ;
        int result = instance.getIdPiece();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getPositions method, of class Piece.
     */
    @Test
    public void testGetPositions() {
        System.out.println("getPositions");
        Piece instance = new Piece(1, new Point (1,1), new Point(1,2));
        List<Point> expResult = new ArrayList<>();
        expResult.add(new Point(1,1));
        expResult.add(new Point(1,2));
        List<Point> result = instance.getPositions();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of length method, of class Piece.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        Piece instance = new Piece(1, new Point(1,1), new Point(1,2));
        int expResult = 2;
        int result = instance.length();
        assertEquals(expResult, result);
 
    }

    

  


  

  
}