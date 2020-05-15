package blokus.model;

import java.util.Arrays;

/*
 * Copyright (C) 2018 g49262
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/**
 * The Board of the game.
 *
 * @author g49262
 */
public class Board {

    private Square[][] board = new Square[20][20];

    /**
     * the constructor of my board.
     */
    public Board() {
        for (Square[] b : board) {
            for (int i = 0; i < b.length; i++) {
                b[i] = new Square();
            }
        }
    }

    /**
     * the getter of my board.
     *
     * @return a square of my board.
     */
    public Square[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    /**
     * get the square compare the point.
     *
     * @param p the point with x and y.
     * @return return the square.
     */
    public Square getElementAt(Point p) {
        return board[p.getX()][p.getY()];
    }

    /**
     * Allows to verify if the point is in the board or not.
     *
     * @param x the abscissa of the point.
     * @param y the ordonate of the point.
     * @return true if the point is in the board.
     */
    public boolean verifyPos(int x, int y) {
        return (x <= 19 && x >= 0) && (y <= 19 && y >= 0);
    }

}
