package blokus.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The class for the Classic move make by a player
 *
 * @author G49262
 */
public class ClassicMove implements MakeMove {

    private Board board;

    /**
     * Constructor of a classic move
     *
     * @param board
     */
    public ClassicMove(Board board) {
        this.board = board;
    }

    /**
     * Method who check if the current player can put a piece in a point of the
     * Game
     *
     * @param piece
     * @param point
     * @param currentPlayer
     * @return
     * @throws BlokusException
     */
    @Override
    public boolean canPut(Piece piece, Point point, Player currentPlayer) {
        List<Point> start = getValidStart(piece, point);
       List<Point> other = getOtherPosition(piece, point);
       boolean checkOther = checkOther(currentPlayer, other);
        boolean checkStart = checkStart(currentPlayer, start);
        boolean b = new Move(board).canPut(piece, point, currentPlayer);
        return checkOther&& b && checkStart;

    }

    /**
     * Method who put a piece of the current player in a point of the game
     *
     * @param piece
     * @param point
     * @param currentPlayer
     * @throws BlokusException
     */
    @Override
    public void putPiece(Piece piece, Point point, Player currentPlayer) {
        for (int i = 0; i < piece.getPositions().size(); i++) {
            setAt(point, piece.getPositions().get(i), currentPlayer, piece);
        }
    }

    /**
     * Set at a point a new Square with the current player and the piece chosen.
     *
     * @param point
     * @param piecePosition
     * @param currentPlayer
     * @param piece
     */
    private void setAt(Point point, Point piecePosition, Player currentPlayer, Piece piece) {
        board.getBoard()[piecePosition.getX() + point.getX()][piecePosition.getY()
                + point.getY()] = new Square(piece, currentPlayer);
    }

    /**
     * Verify if the start is good
     *
     * @param piece
     * @param point
     * @return a list of point valid for the start of a Piece.
     */
    private List<Point> getValidStart(Piece piece, Point point) {
        List<Point> starts = new ArrayList<>();
        for (Point p : piece.getPositions()) {
            starts.addAll(getStarts(p, point));
        }

        return starts;
    }

    /**
     * 
     * @param piece
     * @param point
     * @return 
     */
    
    private List<Point> getOtherPosition(Piece piece, Point point) {
        List<Point> pos = new ArrayList<>();
        for (Point posPiece : piece.getPositions()) {
            List<Point> otherPos = getOthers(posPiece, point);
            pos.addAll(otherPos);
        }

        return pos;

    }

    private List<Point> getOthers(Point posPiece, Point point) {
        Point p1 = new Point(posPiece.getX() + point.getX(), posPiece.getY() + point.getY() - 1);
        Point p2 = new Point(posPiece.getX() + point.getX(), posPiece.getY() + point.getY() + 1);
        Point p3 = new Point(posPiece.getX() + point.getX() - 1, posPiece.getY() + point.getY());
        Point p4 = new Point(posPiece.getX() + point.getX() + 1, posPiece.getY() + point.getY());
        return addValidPos(p1, p2, p3, p4);

    }

    private boolean checkOther(Player currentPlayer, List<Point> other) {
        int i = 0;
        boolean good = true;
        while (i < other.size() && good) {
            Point p = other.get(i);
            Player player = board.getBoard()[p.getX()][p.getY()].getPlayer();
            if (player != null) {
                good = player.getColor() != currentPlayer.getColor();
            }
            i++;
        }
        return good;
    }

    private boolean checkStart(Player currentPlayer, List<Point> start) {
        boolean c = checkNullStart(start);
        int i = 0;
        int goodStart = 0;
        if (c) {
            while (i < start.size() && c) {
                Point p = start.get(i);
                goodStart = check(goodStart, p, currentPlayer);
                i++;
            }
        }
        return goodStart >= 1;
    }

    private boolean checkNullStart(List<Point> starts) {
        int cpt = 0;
        for (Point start : starts) {
            if (this.board.getBoard()[start.getX()][start.getY()].getPlayer() == null) {
                cpt++;
            }
        }
        return cpt != starts.size();
    }

    private int check(int goodStart, Point p, Player currentPlayer) {
        Player player = board.getBoard()[p.getX()][p.getY()].getPlayer();
        if (player != null) {
            if (player.getColor() == currentPlayer.getColor()) {
                goodStart++;
            }
        }
        return goodStart;
    }

    private List<Point> addValidPos(Point... positions) {
        List<Point> pos = new ArrayList<>();
        for (Point posPiece : positions) {
            if (board.verifyPos(posPiece.getX(), posPiece.getY())) {
                pos.add(posPiece);
            }
        }
        return pos;
    }

    private Collection<? extends Point> getStarts(Point p, Point point) {
        Point s1 = new Point(point.getX() - 1 + p.getX(), point.getY() - 1 + p.getY());
        Point s2 = new Point(point.getX() - 1 + p.getX(), point.getY() + 1 + p.getY());
        Point s3 = new Point(point.getX() + 1 + p.getX(), point.getY() - 1 + p.getY());
        Point s4 = new Point(point.getX() + 1 + p.getX(), point.getY() + 1 + p.getY());
        return addValidPos(s1, s2, s3, s4);
    }

}
