package g49262.stratego.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class SquareTypeTest {
    
    @Test
    public void getValueOfWaterTest(){
        SquareType square = SquareType.WATER;
        
        assertEquals(square.getValue(),"WATER");
    }
    
     @Test
    public void getValueOfLandTest(){
        SquareType square = SquareType.LAND;
        
        assertEquals(square.getValue(),"LAND");
    }
}
