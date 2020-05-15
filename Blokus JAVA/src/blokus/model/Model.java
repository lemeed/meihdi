/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokus.model;

import blokus.util.Observer;
import java.util.List;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public interface Model {


    /**
     * the getter of the current player in game.
     *
     * @return Player the player in game.
     */
    public Player currentPlayer();

    /**
     * Allows to take the piece.
     *
     * @param p
     * @param player
     */
    public void selectPiece(Piece p, Player player);

    /**
     * the getter of a square of my board.
     *
     * @return a square of my board
     */
    public Square[][] getBlokusBoard();

    /**
     * The current player in game.
     *
     * @return current player in game.
     */
    public Player getCurrentPlayer();

    /**
     * the getter of the list of player in game.
     *
     * @return the list of players in game.
     */
    public List<Player> getPlayersInGame();



    /**
     * get the piece of the player who choose.
     *
     * @return the piece.
     */
    public Piece getPreviousPiece();

   public boolean playLap(Point position) ;
   public Point getCurrentPos();
    public boolean verifyMove(Point position);

    public void selectPosition(int xforSquare, int yforSquare) throws BlokusException;

    public void setCurrentPos(int xforSquare, int yforSquare);
    public void registerObserver(Observer obs);
    List<Point> positionLive();
    public void nextPLayer();

    public void turnPreviousPiece();

    
    public void turnPreviousPieceMirror();
    public void addPlayer(Player play );
    public boolean endOfTheGame();

    public String getWinner();
      public int incrementPass();
      public String getHistorique();
      public void setHistorique(String historique);
}
