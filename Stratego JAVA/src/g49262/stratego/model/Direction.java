package g49262.stratego.model;

/**
 * This enumeration allows to know a Direction.
 *
 * @author Meihdi El Amouri
 */
public enum Direction {

    UP(-1, 0), // Allows to do a UP move.
    DOWN(1, 0), // Allows to do a DOWN move. 
    LEFT(0, -1), // Allows to do a LEFT move.
    RIGHT(0, 1); // Allows to do a RIGHT move.

    private int row;
    private int column;

    /**
     * The constructor of Diection.
     *
     * @param row
     * @param column
     */
    private Direction(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * The getter of row.
     *
     * @return the row of the direction.
     */
    public int getRow() {
        return row;
    }

    /**
     * The getter of column.
     *
     * @return the column of the direction.
     */
    public int getColumn() {
        return column;
    }

}
