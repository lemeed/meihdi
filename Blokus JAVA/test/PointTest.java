/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import blokus.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */


public class PointTest {
    
    public PointTest() {
    }

    /**
     * Test of getX method, of class Point.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        Point instance = new Point(1, 2);
        int expResult = 1;
        int result = instance.getX();
        assertEquals(expResult, result);
   
        
    }

    /**
     * Test of getY method, of class Point.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        Point instance = new Point (1,2);
        int expResult = 2;
        int result = instance.getY();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEqualsFalse() {
        System.out.println("equals");
        Point other = new Point(1,2);
        Point instance = new Point(2,1);
        boolean expResult = false;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
        
    }
        /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEqualsTrue() {
        System.out.println("equals");
        Point other = new Point(1,2);
        Point instance = new Point(1,2);
        boolean expResult = true;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
        
    }

    
}
