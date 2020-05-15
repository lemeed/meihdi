package g49262.stratego.model;

/**
 * The position in the board.
 *
 * @author Meihdi El Amouri
 */
public class Position {

    public int row;
    public int column;

    /**
     * this is the constructor of Position in the board.
     *
     * @param aRow the row in the board.
     * @param aColumn the column in the board.
     */
    public Position(int aRow, int aColumn) {
        this.row = aRow;
        this.column = aColumn;
    }

    /**
     * the getter of row.
     *
     * @return row in the board.
     */
    public int getRow() {
        return row;
    }

    /**
     * The getter of column.
     *
     * @return column in the board.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Allows to know which position after the move.
     *
     * @param dir the direction who want to move.
     * @return the position after the move.
     */
    public Position next(Direction dir) {
        Position p = new Position(this.row + dir.getRow(), this.column + dir.getColumn());
        return p;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.row;
        hash = 67 * hash + this.column;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.row != other.row) {
            return false;
        }
        return this.column == other.column;
    }

}
