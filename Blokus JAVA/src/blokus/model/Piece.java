/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The piece of the player.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Piece {

    private int idPiece;
    private List<Point> positions = new ArrayList<>();

    /**
     * The constructor of piece with no argument.
     */
    public Piece() {
        this.idPiece = 0;
        this.positions = null;
    }

    /**
     * The constructor of Piece.
     *
     * @param idPiece the number of piece in the stock.
     * @param positions the arrays who contains the points.
     */
    public Piece(int idPiece, Point... positions) {
        this.idPiece = idPiece;
        initPos(positions);
    }

    /**
     * Constructor of a Piece object for the blokus game
     *
     * @param idPiece
     * @param positions
     */
    public Piece(int idPiece, List<Point> positions) {
        this.idPiece = idPiece;
        this.positions = positions;

    }

    /**
     * The id piece getter.
     *
     * @return idPiece.
     */
    public int getIdPiece() {
        return idPiece;
    }

    public void setIdPiece(int idPiece) {
        this.idPiece = idPiece;
    }

    /**
     * The getter of position.
     *
     * @return a list of points.
     */
    public List<Point> getPositions() {
        return new ArrayList<>(positions);
    }

    /**
     * the length of the point in the shape.
     *
     * @return
     */
    public int length() {
        return positions.size();
    }

    /**
     * Change the arrays position in list of position for make a piece.
     *
     * @param position
     */
    private void initPos(Point[] position) {
        positions.addAll(Arrays.asList(position));
    }

    @Override
    public String toString() {
        return ("" + idPiece);
    }

    /**
     * Allows to turn the piece to 90°.
     *
     * @return
     */
    public Piece turnPiece() {
        List<Point> pTurn = new ArrayList<>();
        this.getPositions().forEach((p) -> {
            p.swap();
        });
        return new Piece(this.idPiece, pTurn);
    }

    /**
     * Allows to turn the Piece like a mirror.
     *
     * @return
     */
    public Piece turnPieceMirror() {
        List<Point> pTurn = new ArrayList<>();
        this.getPositions().forEach((p) -> {
            p.mirror();
        });
        return new Piece(this.idPiece, pTurn);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Piece other = (Piece) obj;
        if (this.idPiece != other.idPiece) {
            return false;
        }
        if (!Objects.equals(this.positions, other.positions)) {
            return false;
        }
        return true;
    }

}
