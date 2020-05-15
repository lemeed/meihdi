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
public class FirstMove implements MakeMove {

    private Board board;

    public FirstMove(Board board) {

        this.board = board;
    }

    @Override
    public boolean canPut(Piece piece, Point point, Player currentPlayer) {
        boolean b = false;
        if (point.equals(currentPlayer.getStart())) {

            b = new Move(board).canPut(piece, point, currentPlayer);
        }
        return b;
    }

    @Override
    public void putPiece(Piece piece, Point point, Player currentPlayer) {
        for (int i = 0; i < piece.getPositions().size(); i++) {
            setAt(point, piece.getPositions().get(i), currentPlayer, piece);
        }
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

}
