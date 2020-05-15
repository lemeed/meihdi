/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import blokus.model.BlokusException;
import blokus.model.Board;
import blokus.model.Game;
import blokus.model.Model;
import blokus.model.Piece;
import blokus.model.Player;
import blokus.model.Point;
import blokus.model.Square;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class GameTest {

 
    @Test
    public void currentPlayerTest() {
        Model game = new Game();
        Player play = new Player("med", Color.CORAL, 0, new Point(0, 0));
        game.addPlayer(play);
        Player test = game.getCurrentPlayer();

        assertEquals(play, test);
    }

    @Test
    public void selectPieceTestAndGetPreviousPiece() {
        Model game = new Game();
        Player play = new Player("med", Color.CORAL, 0, new Point(0, 0));
        game.selectPiece(play.getStock().getAPiece(0), play);
        assertEquals(game.getPreviousPiece(), play.getStock().getAPiece(0));

    }

    @Test
    public void getBlokusBoardTest() {
        Model game = new Game();
        Board b = new Board();
        assertEquals(game.getBlokusBoard(), b.getBoard());
    }

    @Test
    public void getPlayersInGame() {
        Model game = new Game();
        Player play = new Player("med", Color.CORAL, 0, new Point(0, 0));
        game.addPlayer(play);
        assertEquals(game.getPlayersInGame().size(), 1);
    }

    @Test
    public void getPlayersInGameTwo() {
        Model game = new Game();
        Player play = new Player("med", Color.CORAL, 0, new Point(0, 0));
        game.addPlayer(play);
        assertEquals(game.getPlayersInGame().get(0).getName(), play.getName());
    }

    @Test
    public void playLapTest() {
        Model game = new Game();
        Player play1 = new Player("l", Color.RED, 1, new Point(19, 0));
        Player play2 = new Player("m", Color.BLUE, 2, new Point(0, 19));
        Player play3 = new Player("m", Color.YELLOW, 3, new Point(19, 19));
        Player play4 = new Player("m", Color.GREEN, 4, new Point(0, 0));
        game.addPlayer(play1);
        game.addPlayer(play2);
        game.addPlayer(play3);
        game.addPlayer(play4);
        //game.selectPiece(play1.getStock().getAPiece(1), play1);

        game.selectPiece(play1.getStock().getAPiece(1), play1);

        assertTrue(game.playLap(play1.getStart()));
    }

    @Test
    public void getCurrentPosTest() {
        Model g = new Game();
        Point p = g.getCurrentPos();
        assertEquals(new Point(10, 10), p);
    }

    @Test
    public void positionLive() {
        Model game = new Game();
        Player play1 = new Player("l", Color.RED, 1, new Point(19, 0));
        Player play2 = new Player("m", Color.BLUE, 2, new Point(0, 19));
        Player play3 = new Player("m", Color.YELLOW, 3, new Point(19, 19));
        Player play4 = new Player("m", Color.GREEN, 4, new Point(0, 0));
        game.addPlayer(play1);
        game.addPlayer(play2);
        game.addPlayer(play3);
        game.addPlayer(play4);
        game.selectPiece(play1.getStock().getAPiece(1), play1);
        assertEquals(game.positionLive().size(),1);

    }
    
    @Test
    public void nextPlayerTest(){
        Model game = new Game();
        Player play1 = new Player("l", Color.RED, 1, new Point(19, 0));
        Player play2 = new Player("m", Color.BLUE, 2, new Point(0, 19));
        Player play3 = new Player("m", Color.YELLOW, 3, new Point(19, 19));
        Player play4 = new Player("m", Color.GREEN, 4, new Point(0, 0));
        game.addPlayer(play1);
        game.addPlayer(play2);
        game.addPlayer(play3);
        game.addPlayer(play4);
        game.nextPLayer();
        assertEquals(game.getCurrentPlayer(),play2);
    }
    
     @Test
    public void IfWeNextPlayerButItsTheLast(){
        Model game = new Game();
        Player play1 = new Player("l", Color.RED, 1, new Point(19, 0));
        Player play2 = new Player("m", Color.BLUE, 2, new Point(0, 19));
        Player play3 = new Player("m", Color.YELLOW, 3, new Point(19, 19));
        Player play4 = new Player("m", Color.GREEN, 4, new Point(0, 0));
        game.addPlayer(play1);
        game.addPlayer(play2);
        game.addPlayer(play3);
        game.addPlayer(play4);
        game.nextPLayer();
        game.nextPLayer();
        game.nextPLayer();
        game.nextPLayer();
        assertEquals(game.getCurrentPlayer(),play1);
    }
    
    @Test 
    public void testToAdd4Players(){
         Model game = new Game();
        Player play1 = new Player("l", Color.RED, 1, new Point(19, 0));
        Player play2 = new Player("m", Color.BLUE, 2, new Point(0, 19));
        Player play3 = new Player("m", Color.YELLOW, 3, new Point(19, 19));
        Player play4 = new Player("m", Color.GREEN, 4, new Point(0, 0));
        game.addPlayer(play1);
        game.addPlayer(play2);
        game.addPlayer(play3);
        game.addPlayer(play4);
        assertEquals(game.getPlayersInGame().size(),4);
    }
    @Test
    public void testWhoWonTheGame(){
         Model game = new Game();
        Player play1 = new Player("l", Color.RED, 1, new Point(19, 0));
        Player play2 = new Player("m", Color.BLUE, 2, new Point(0, 19));
        Player play3 = new Player("m", Color.YELLOW, 3, new Point(19, 19));
        Player play4 = new Player("m", Color.GREEN, 4, new Point(0, 0));
        game.addPlayer(play1);
        game.addPlayer(play2);
        game.addPlayer(play3);
        game.addPlayer(play4);
        play1.setScore(42);
        assertEquals("l",game.getWinner());
    }
    
}
