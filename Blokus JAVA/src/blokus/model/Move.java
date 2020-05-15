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
public class Move implements MakeMove {

    private Board board;

    /**
     * Constructor of a move
     *
     * @param board
     */
    public Move(Board board) {
        this.board = board;
    }

    /**
     * Get the board of the game
     *
     * @return the board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Method to verify if we can put a piece in a position
     *
     * @param piece
     * @param point
     * @param currentPlayer
     * @return True if we can and false we can't
     * @throws BlokusException
     */
    @Override
    public boolean canPut(Piece piece, Point point, Player currentPlayer) {
        boolean b = true;
        int i = 0;
        while (i < piece.getPositions().size() && b) {
            b = validMove(piece.getPositions().get(i), point);
            i++;
        }
        return b;
    }

    /**
     * Put a piece in a position after verify it
     *
     * @param piece
     * @param point
     * @param currentPlayer
     */
    @Override
    public void putPiece(Piece piece, Point point, Player currentPlayer) {

        for (int i = 0; i < piece.getPositions().size(); i++) {
            setAt(point, piece.getPositions().get(i), currentPlayer, piece);
        }

    }

    /**
     * Check if the move we make is good or not
     *
     * @param positionPiece
     * @param position
     * @return true if the move is valid and false if it's not
     */
    private boolean validMove(Point positionPiece, Point position) {
        boolean b = false;
        if (!notInside(positionPiece, position)) {
            b = new Square().equals(board.getBoard()[positionPiece.getX()
                    + position.getX()][positionPiece.getY() + position.getY()]);
        }
        return b;
    }

    /**
     * Create a new Square with the place and currentPlayer.
     *
     * @param point the point who want to add the piece.
     * @param piecePosition a point of the piece.
     * @param currentPlayer the current player in game.
     * @param piece the piece that the player choose.
     */

    private void setAt(Point point, Point piecePosition, Player currentPlayer, Piece piece) {
        board.getBoard()[piecePosition.getX() + point.getX()][piecePosition.getY()
                + point.getY()] = new Square(piece, currentPlayer);
    }

    /**
     * Verify if the position is inside the board
     *
     * @param positionPiece
     * @param position
     * @return true if the position is inside the board of the game
     */
    private boolean notInside(Point positionPiece, Point position) {
        return !board.verifyPos(positionPiece.getX() + position.getX(), positionPiece.getY() + position.getY());
    }

    /**
     * Method to move from a point to another one
     *
     * @param point
     * @param point2
     */
    void move(Point point, Point point2) {
        Piece piece = board.getElementAt(point).getPiece();
        Player player = board.getBoard()[point.getX()][point.getY()].getPlayer();

        putPiece(piece, point2, player);
    }

}
