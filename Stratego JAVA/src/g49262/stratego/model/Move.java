package g49262.stratego.model;

/**
 * Allows to know the move of the piece.
 *
 * @author Meihdi El Amouri
 */
public class Move {

    private Piece piece;
    private Position start;
    private Position end;

    /**
     * This is the constructor of the Move.
     *
     * @param piece the piece that we want to move.
     * @param start the start position of the piece.
     * @param end the position who want to move the piece.
     */
    public Move(Piece piece, Position start, Position end) {
        if (piece == null || start == null || end == null) {
            throw new NullPointerException("one of the attribute is null");
        }
        this.piece = piece;
        this.start = start;
        this.end = end;
    }

    /**
     * This is the getter of the piece
     *
     * @return the piece who want to move.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * This is the getter of start position.
     *
     * @return the position of the start.
     */
    public Position getStart() {
        return start;
    }

    /**
     * This is the getter of the end position.
     *
     * @return the position of the end.
     */
    public Position getEnd() {
        return end;
    }

}
