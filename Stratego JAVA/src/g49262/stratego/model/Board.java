package g49262.stratego.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The board of the game.
 *
 * @author Meihdi El Amouri
 */
public class Board {

    Square[][] squares;
    public static final int row = 7;
    public static final int col = 6;

    /**
     * This is the constructor of the class board. Allows to initialize a empty
     * board.
     */
    public Board() {

        this.squares = new Square[row][col];

        squares[2][1] = new Square(null, SquareType.WATER);
        squares[2][2] = new Square(null, SquareType.WATER);
        squares[2][3] = new Square(null, SquareType.WATER);

        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 5; j++) {

                if (this.squares[i][j] == null) {
                    squares[i][j] = new Square(null, SquareType.LAND);
                }

            }
        }

    }

    /**
     * This is the getter of the board.
     *
     * @return The board of the game.
     */
    public Square[][] getSquares() {
        return squares;
    }

    /**
     * Allows to know if the position is inside the board.
     *
     * @param position the position who the player wants to play.
     * @return true if the position is inside the board.
     */
    public boolean isInside(Position position) {

        if (position.getRow() > 6 || position.getRow() < 0) {
            return false;
        } else if (position.getColumn() > 5 || position.getColumn() < 0) {
            return false;
        }
        return true;
    }

    /**
     * This is the getter of square.
     *
     * @param position the position who are positionning the square.
     * @return the square of the position in parameter.
     */
    public Square getSquare(Position position) {
        if (!isInside(position)) {
            throw new IllegalArgumentException("Sorry but the position is not correct");
        }
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Allows to put the piece in a position.
     *
     * @param piece the piece that the player choose to put.
     * @param position the position that the position choose to put.
     */
    public void put(Piece piece, Position position) {
        if (piece == null) {
            throw new NullPointerException("sorry but there is no position to put ");
        } else if (!isInside(position)) {
            throw new IllegalArgumentException("Sorry but you are not inside the board");
        }

        this.getSquare(position).put(piece);

    }

    /**
     * Allows to know if the square is empty or not.
     *
     * @param position the position of the square in the board.
     * @return true if they are nothing in the square.
     */
    public boolean isFree(Position position) {

        if (!isInside(position)) {
            throw new IllegalArgumentException("The position is not good");
        }
        return this.getSquare(position).isFree();
    }

    /**
     * Allows to know if in the square they are a piece with the same color
     * player.
     *
     * @param position the position of the square in the board.
     * @param color the color of player.
     * @return true if they are the same color.
     */
    public boolean isMyOwn(Position position, PlayerColor color) {
        if (!isInside(position)) {
            throw new IllegalArgumentException("The position is not good");
        }
        return this.getSquare(position).isMyOwn(color);
    }

    /**
     * Allows to get a piece.
     *
     * @param position the position of the square in the board.
     * @return the piece at the position.
     */
    public Piece getPiece(Position position) {
        if (!isInside(position)) {
            throw new IllegalArgumentException(" The position is not good");
        }
        return getSquare(position).getPiece();
    }

    /**
     * Allows to remove a piece in the board.
     *
     * @param position the position where we want to remove.
     */
    public void remove(Position position) {
        if (!this.squares[position.getRow()][position.getColumn()].isFree()) {
            this.squares[position.getRow()][position.getColumn()].remove();
        }
    }

    /**
     * Allows to know the position of the pieces of player.
     *
     * @param play the player.
     * @return all the position of the pieces of player in the board.
     */
    public List<Position> getTakenSquare(Player play) {
        List<Position> p = new ArrayList<>();
        for (int i = 0; i <= squares.length - 1; i++) {
            for (int j = 0; j <= squares[0].length - 1; j++) {

                if (squares[i][j].isMyOwn(play.color)) {
                    Position pos = new Position(i, j);
                    p.add(pos);
                }
            }
        }
        return p;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Arrays.deepHashCode(this.squares);
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
        final Board other = (Board) obj;
        return Arrays.deepEquals(this.squares, other.squares);
    }

}
