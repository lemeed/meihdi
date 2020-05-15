package blokus.model;

import blokus.util.Observable;
import blokus.util.Observer;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Allows the play the game.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>F
 */
public class Game implements Model, Observable {

    private List<Player> playerInGame;
    public Board board;
    private Piece previousPiece;
    private int currentPlayer;
    private Point currentPos;
    private List<Observer> observers;
    private Point currentPosBot;
    private int passed;
    private String historique; 

    /**
     * the constructor of the game.
     */
    public Game() {
        playerInGame = new ArrayList<>();
        initialGame();
        this.passed = 0;
        this.observers = new ArrayList<>();
        this.currentPos = new Point(10, 10);
        this.historique = "";

    }

    /**
     * initialize the board , currentPLayer and the previousPiece
     */
    private void initialGame() {
        this.board = new Board();

        this.currentPlayer = 0;
        this.previousPiece = null;
    }

    @Override
    public void addPlayer(Player play) {
        playerInGame.add(play);
        play.getStock().createPieces();

    }

    /**
     * the getter of the list of player.
     *
     * @return
     */
    @Override
    public List<Player> getPlayersInGame() {
        return new ArrayList<>(playerInGame);

    }

    @Override
    public Point getCurrentPos() {
        return currentPos;
    }

    /**
     * the getter of the current player in game.
     *
     * @return Player the player in game.
     */
    @Override
    public Player getCurrentPlayer() {
        return playerInGame.get(currentPlayer);
    }

    /**
     * the getter of a square of my board.
     *
     * @return a square of my board
     */
    @Override
    public Square[][] getBlokusBoard() {
        return board.getBoard();
    }

    @Override
    public void setCurrentPos(int x, int y) {
        this.currentPos = new Point(x, y);
    }

    /**
     * get the piece of the player who choose.
     *
     * @return the piece.
     */
    @Override
    public Piece getPreviousPiece() {
        return previousPiece;

    }
    /**
     * Allows to get the historique.
     * @return 
     */
    @Override
    public String getHistorique() {
        return historique;
    }

    /**
     * Allows to set the historique a new line.
     * @param historique 
     */
    public void setHistorique(String historique) {
        this.historique += historique +"\n";
    }

    
    /**
     * Allows to update the historique.
     */
    public void updateHisto(){
        historique +=" Joueur "+getCurrentPlayer().getIdPlayer() + " Pièce  "+ getPreviousPiece().getIdPiece()+"\n"
                 ;
    }
   
    


    /**
     * The current player in game.
     *
     * @return current player in game.
     */
    @Override
    public Player currentPlayer() {

        return playerInGame.get(currentPlayer);
    }

    /**
     * Allows to pick a piece in the deck.
     *
     * @param p
     * @param player
     */
    @Override
    public void selectPiece(Piece p, Player player) {
        if (p != null && player.equals(this.currentPlayer())) {
            previousPiece = p;
            notifyObservers();
        }

    }

    /**
     * Allows to know if we can put the piece at the start or for a classic
     * move.
     *
     * @param position
     * @return true if we can.
     */
    
    @Override
    public boolean verifyMove(Point position) {

        MakeMove move ;
        boolean b;
        if (this.currentPlayer().getStock().getStock().size() == 22) {
            move = new FirstMove(board);
        } else {
            move = new ClassicMove(board);
        }
        b = move.canPut(previousPiece, position, this.getCurrentPlayer());
        return b;

    }

    /**
     * can check if we can add this position and if we can allows to put the
     * piece in the board.
     *
     * @param position the position where we want to put the piece.
     * @return true if we can add the piece in the board.
     */
    @Override
    public boolean playLap(Point position) {
        boolean b = verifyMove(position);
        if (b) {
            updateHisto();
            updateGame(position);
           
            currentPos = new Point(10, 10);

        }

        notifyObservers();
        previousPiece = null;

        return b;
    }

    /**
     * Put the piece in the board .
     *
     * @param position the position who put the piece.
     */
    private void updateGame(Point position) {

        new Move(board).putPiece(previousPiece, position, getCurrentPlayer());

        addScore(playerInGame.get(currentPlayer), previousPiece.getPositions().size());

        getCurrentPlayer().getStock().getStock().remove(previousPiece);
        addScore(playerInGame.get(currentPlayer), previousPiece.getPositions().size());
        nextPLayer();
        numberPiece();
        passed = 0;
        notifyObservers();
    }

    /**
     * Allows to put a position in the board and playIt if it's right
     *
     * @param x the abscissa
     * @param y the ordonate
     * @throws BlokusException
     */
    @Override
    public void selectPosition(int x, int y) throws BlokusException {
        if (previousPiece != null) {
            currentPos = new Point(x,y);
            this.playLap(new Point(x, y));
        }
        
    }

    /**
     * Allows to know the end of the game.
     *
     * @return true if it's the end.
     */
    @Override
    public boolean endOfTheGame() {
        return passed == 4;

    }

    /**
     * allows to know if the currentPlayer has no piece.
     *
     * @return true if the currentPlayer has no piece.
     */
    private boolean numberPiece() {
        boolean b = getCurrentPlayer().getStock().getStock().isEmpty();
        if (getCurrentPlayer().getStock().getStock().isEmpty()) {
            System.out.println("Le joueur n'a plus de pièces !"
                    + "Recommencez une nouvelle partie ");
        }
        return b;
    }

    /**
     * Add score to player.
     */
    public void addScore(Player aPlayer, int score) {
        aPlayer.setScore(score);
    }

    @Override
    public void registerObserver(Observer obs) {
        if (!observers.contains(obs)) {
            observers.add(obs);
        }
    }

    @Override
    public void removeObserver(Observer obs) {
        if (observers.contains(obs)) {
            observers.remove(obs);
        }
    }

    @Override
    public void notifyObservers() {
        observers.forEach((obs) -> {
            obs.update(this);
        });
    }

    /**
     * Allows to know the live position of my piece in the board.
     *
     * @return
     */
    @Override
    public List<Point> positionLive() {
        List<Point> l = new ArrayList();
        if (previousPiece != null) {
            previousPiece.getPositions().stream().forEach((pos) -> {
                l.add(new Point(currentPos.getX() + pos.getX(), currentPos.getY() + pos.getY()));
            });
        }
        return l;
    }

    /**
     * Change the player and for the bot play the piece.
     */
    @Override
    public void nextPLayer() {
        currentPlayer = currentPlayer + 1;

        if (currentPlayer >= playerInGame.size()) {
            currentPlayer = 0;
        }
        if (getCurrentPlayer().getStock().getStock().isEmpty()) {
            nextPLayer();
        }
        endOfTheGame();

        if (getCurrentPlayer().isBot()) {
            System.out.println(playBot());
            playLap(currentPosBot);
        }
        notifyObservers();
    }

    /**
     * turn the piece to 90°.
     */
    @Override
    public void turnPreviousPiece() {
        if (previousPiece != null) {
            previousPiece.turnPiece();
            notifyObservers();
        }

    }

    /**
     * Turn the piece like a mirror.
     */
    @Override
    public void turnPreviousPieceMirror() {
        if (previousPiece != null) {
            previousPiece.turnPieceMirror();
            notifyObservers();
        }

    }

    /**
     * Allows to get the point to place the piece
     *
     * @param p the random piece.
     * @return the point to place the piece
     */
    private boolean theGoodPoint(Piece p) {

        MakeMove move = new ClassicMove(board);

        if (getCurrentPlayer().getStock().getStock().size() == 21) {
            move = new FirstMove(board);
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (move.canPut(p, new Point(i, j), getCurrentPlayer())) {
                    if (getCurrentPlayer().isBot()) {
                        currentPosBot = new Point(i, j);
                        return true;
                    }

                    return true;

                }
            }
        }
        return false;
    }

    @Override
    public String getWinner() {
        int max = 0;

        String winner = null;

        for (Player play : this.playerInGame) {
            if (play.getScore() > max) {

                max = play.getScore();
            }
        }
        for (Player myExplorer : playerInGame) {
            if (myExplorer.getScore() == max) {
                winner = myExplorer.getName();
            }

        }
        return winner;
    }

    /**
     * play the bot
     *
     * @return true if the bot can play.
     */
    private boolean playBot() {
        List<Piece> stock = getCurrentPlayer().getStock().getStock();
        for (int i = 0; i < 10; i++) {

            selectPiece(stock.get((int) (Math.random() * (((stock.size() - 1) - 0) + 1) + 0)), getCurrentPlayer());
            for (int k = 0; k <= 1; k++) {
                for (int j = 0; j < 4; j++) {
                    if (theGoodPoint(previousPiece)) {
                        this.passed = 0;
                        return true;
                    }
                    turnPreviousPiece();
                }
                turnPreviousPieceMirror();
            }
        }
        incrementPass();
        return false;
    }

    @Override
    public int incrementPass() {
        return this.passed++;
    }
}
