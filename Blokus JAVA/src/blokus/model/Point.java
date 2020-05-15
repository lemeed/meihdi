/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.model;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Point {

    private int x;
    private int y;

    /**
     * the constructor of point.
     *
     * @param x
     * @param y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * the getter of x.
     *
     * @return the absissa of the point.
     */
    public int getX() {
        return x;

    }

    /**
     * the getter of y.
     *
     * @return y the ordonate of the point.
     */
    public int getY() {
        return y;
    }

    /**
     * Allows to swap x and y;
     */
    public void swap() {
        int nb = x;
        x = y;
        y = 0 - nb;
    }

    /**
     * Change the x and y points to negative.
     */
    public void mirror() {
        x = (-x);
        y = (-y);
    }

    /**
     * Method who compares 2 points and verify if they're equal
     *
     * @param obj
     * @return true if the points are equals and false if they don't
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }

}
