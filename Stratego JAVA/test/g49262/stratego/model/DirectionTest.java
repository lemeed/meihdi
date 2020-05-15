
package g49262.stratego.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class DirectionTest {

    public DirectionTest() {
    }

    @Test
    public void testGetRowDOWN() {
        Direction d = Direction.DOWN;
        assertEquals(d.getRow(), 1);
    }

    @Test
    public void testGetRowUP() {
        Direction d = Direction.UP;
        assertEquals(d.getRow(), -1);
    }

    @Test
    public void testGetColumnRIGHT() {
        Direction d = Direction.RIGHT;
        assertEquals(d.getColumn(), 1);
    }

    @Test
    public void testGetColumnLEFT() {
        Direction d = Direction.LEFT;
        assertEquals(d.getColumn(), -1);
    }

}
