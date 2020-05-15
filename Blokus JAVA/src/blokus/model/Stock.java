/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Stock of the player.
 *
 * @author g49262
 */
public final class Stock {

    private List<Piece> stock;

    /**
     * Constructor of a stock of pieces for a player
     */
    public Stock() {
        this.stock = new ArrayList<>();
        createPieces();
    }

    /**
     * Create pieces for the stock of a player
     */
    public void createPieces() {
        stock.clear();
        stock.add(new Piece(1, new Point(0, 0)));
        stock.add(new Piece(2, new Point(0, 0), new Point(1, 0)));
        stock.add(new Piece(3, new Point(0, 0), new Point(1, 0), new Point(2, 0)));
        stock.add(new Piece(4, new Point(0, 0), new Point(0, 1), new Point(1, 0)));
        stock.add(new Piece(5, new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)));
        stock.add(new Piece(6, new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(1, 2)));
        stock.add(new Piece(7, new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 1)));
        stock.add(new Piece(8, new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)));
        stock.add(new Piece(9, new Point(1, 0), new Point(0, 1), new Point(2, 0), new Point(1, 1)));
        stock.add(new Piece(10, new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3), new Point(0, 4)));
        stock.add(new Piece(11, new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)));
        stock.add(new Piece(12, new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2), new Point(1, 3)));
        stock.add(new Piece(13, new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)));
        stock.add(new Piece(14, new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)));
        stock.add(new Piece(15, new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 2), new Point(0, 3)));
        stock.add(new Piece(16, new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(1, 1), new Point(1, 2)));
        stock.add(new Piece(17, new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(0, 2)));
        stock.add(new Piece(18, new Point(0, 2), new Point(1, 2), new Point(1, 1), new Point(2, 1), new Point(2, 0)));
        stock.add(new Piece(19, new Point(0, 1), new Point(0, 2), new Point(1, 1), new Point(2, 1), new Point(2, 0)));
        stock.add(new Piece(20, new Point(0, 1), new Point(0, 2), new Point(1, 1), new Point(2, 1), new Point(1, 0)));
        stock.add(new Piece(21, new Point(0, 1), new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)));
        stock.add(new Piece(22 , new Point(0,0),new Point(1,1), new Point(1,0),new Point(0,1), new Point(0,2),new Point(1,2)));

    }

    /**
     * Get the stock of pieces for a player
     *
     * @return
     */
    public List<Piece> getStock() {
        return stock;
    }

    /**
     * Get a piece with the id given in parameters
     *
     * @param idPiece
     * @return the piece in the id.
     */
    public Piece getAPiece(int idPiece) {
        Piece p = null;
        for (Piece chosePiece : stock) {
            if (chosePiece.getIdPiece() == idPiece) {
                p = chosePiece;
            }
        }
        return p;
    }

    /**
     * Allows to remove the piece of the stock.
     *
     * @param p
     */
    public void remove(Piece p) {
        stock.remove(p);
    }

    @Override
    public String toString() {
        return "stock=" + stock + '}';
    }

    void addPiece(Piece p) {
        stock.add(p);
    }

}
