package g49262.stratego.model;

import java.util.Objects;

/**
 * The piece of the player in the board.
 *
 * @author Meihdi El Amouri
 */
public class Piece {

    private int rank;
    private PlayerColor color;
    private int nbSteps;

    /**
     * This is the constructor of a piece.
     *
     * @param aRank the rank of the piece.
     * @param aColor the color of the piece.
     */
    public Piece(int aRank, PlayerColor aColor) {
        this.rank = aRank;
        this.color = aColor;
        this.nbSteps = 1;
    }

    public Piece(int aRank, int aNbSteps, PlayerColor aColor) {
        if (aNbSteps < 0) {
            throw new IllegalArgumentException("Sorry but the number of steps is not respected");
        }

        this.rank = aRank;
        this.nbSteps = aNbSteps;
        this.color = aColor;
    }

    public int getNbSteps() {
        return nbSteps;
    }

    /**
     * This is the getter of the color of the piece.
     *
     * @return the color of the piece.
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Allows to know who's stronger.
     *
     * @param other the piece that we are attacking.
     * @return true if selected piece is stronger.
     */
    public boolean isStronger(Piece other) {
        return other.rank < this.rank;
    }

    /**
     * Allows to know if they are the same rank.
     *
     * @param other the piece that we are attacking.
     * @return true if they are the same rank.
     */
    public boolean hasSameRank(Piece other) {
        return other.rank == this.rank;
    }

    /**
     * The getter of the piece rank.
     *
     * @return the rank of the piece.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Allows to know if the piece can cross the square.
     *
     * @param square the square who want to cross.
     * @return true if he can.
     */
    public boolean canCross(Square square) {
        return square.getType() == SquareType.LAND;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.rank;
        hash = 79 * hash + Objects.hashCode(this.color);
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
        final Piece other = (Piece) obj;
        if (this.rank != other.rank) {
            return false;
        }
        return this.color == other.color;
    }

}
