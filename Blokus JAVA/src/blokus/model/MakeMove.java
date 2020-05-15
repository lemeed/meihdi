package blokus.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public interface MakeMove {
    /**
 * Create a method to verify if we can put a piece at a position
 * @param piece
 * @param point
 * @param currentPlayer
 * @return true if we can and false if we can't put it 
 */
    boolean canPut(Piece piece, Point point, Player currentPlayer) ;
/**
 * 
 * Method to put a piece at a position 
 * @param piece
 * @param point
 * @param currentPlayer
 */
    public void putPiece(Piece piece, Point point, Player currentPlayer);
    
}
