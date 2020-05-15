package g49262.stratego.model;

import java.util.Objects;

/**
 * The square of the board.
 *
 * @author Meihdi El Amouri
 */
public class Square {

    private Piece piece;
    private SquareType type;

    /**
     * This is the constructor of Square.
     *
     * @param aPiece The piece in the square.
     * @param aType the type of the piece.
     */
    public Square(Piece aPiece, SquareType aType) {
        this.piece = aPiece;
        this.type = aType;
    }

    /**
     * The constructor by default.
     */
    public Square() {
        this.piece = null;
    }

    /**
     * this is the getter of the piece.
     *
     * @return The piece in the square Null if it's empty.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * this is the getter of the type.
     *
     * @return the type of the square.
     */
    public SquareType getType() {
        return type;
    }

    /**
     * Allows to know if they are a pieces in the square.
     *
     * @return true if the square is free.
     */
    public boolean isFree() {
        return this.piece == null;
    }

    /**
     * Allows to put a piece in the board.
     *
     * @param aPiece the piece who want to put in the square.
     */
    public void put(Piece aPiece) {
        if (aPiece == null) {
            throw new NullPointerException("Sorry but there are no piece to put");
        } else if (!this.isFree()) {
            throw new IllegalStateException("Sorry but they are already a piece in this square");
        }

        this.piece = aPiece;

    }

    /**
     * Allows to know if the square is the same color that player.
     *
     * @param color the player of color.
     * @return true if the piece in the square is the same color that player.
     */
    public boolean isMyOwn(PlayerColor color) {
        if (this.piece == null) {
            return false;
        }
        return color == this.piece.getColor();

    }

    /**
     * Allows to know if the square is a land or not.
     *
     * @return true if the square is a land.
     */
    public boolean isLand() {
        return this.type == SquareType.LAND;
    }

    /**
     * Allows to remove a piece in a square
     *
     */
    public void remove() {
        this.piece = null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.piece);
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
        final Square other = (Square) obj;
        return Objects.equals(this.piece, other.piece);
    }

}
