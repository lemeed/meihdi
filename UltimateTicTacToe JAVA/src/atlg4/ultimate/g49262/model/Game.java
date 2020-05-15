/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.model;

import atlg4.ultimate.g49262.db.DBManager;
import atlg4.ultimate.g49262.exception.DBException;
import atlg4.ultimate.g49262.exception.DTOException;
import atlg4.ultimate.g49262.pers.PlayerDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Represent a game of UTT.
 *
 * @author Meihdi El Amouri <49262@etu.he2b.be>
 */
public class Game extends Observable implements GameModel {

    private Player currentPlayer;

    private List<Player> playingPlayers;

    private UltimateTicTacToe utt;

    private DBManager database;

    private boolean isOver;

    /**
     * The constructor of game.
     */
    public Game() {

        this.utt = new UltimateTicTacToe();
        this.playingPlayers = new ArrayList<Player>();
        this.isOver = false;
        database = new DBManager();

    }

    /**
     * Allows to add player in the list.
     * @param name the name of player.
     */
    public void addPlayer(String name) {
        playingPlayers.add(new Player(name, RandomSym()));
        currentPlayer = playingPlayers.get(0);
    }

    @Override
    public UltimateTicTacToe getUtt() {
        return utt;
    }

    /**
     * Allows to get the dataBase.
     * @return the database.
     */
    public DBManager getDatabase() {
        return database;
    }

    @Override
    public void isGameOver() {

        this.isOver = utt.isFinished();

    }

    /**
     * Allows set the game over.
     * @param isOver true if is over.
     */
    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void nextPlayer() {
        if (currentPlayer == playingPlayers.get(0)) {
            currentPlayer = playingPlayers.get(1);
        } else {
            currentPlayer = playingPlayers.get(0);
        }
    }

    @Override
    public void giveUp() {
        playingPlayers.remove(currentPlayer);

    }

    @Override
    public void putSymbol(int partUTT, int partMTT) {
        if (utt.canPlayPart(partUTT) && getCurrentPlayer().getJoker()==false ) {
            System.out.println("pas bon ");
            utt.getLastMoveBoardPart().playpart(partMTT, currentPlayer.getSymbol());
            if (!utt.getBoardParts().get(partMTT).isFinished()) {
                utt.setLastMoveBoardPart((MiniTicTacToe) utt.getBoardParts().get(partMTT));
            }

            nextPlayer();
        }else if(getCurrentPlayer().getJoker() == true){
            utt.getLastMoveBoardPart().playPartJoker(partMTT, currentPlayer.getSymbol());
            if (!utt.getBoardParts().get(partMTT).isFinished()) {
                utt.setLastMoveBoardPart((MiniTicTacToe) utt.getBoardParts().get(partMTT));
            }
            getCurrentPlayer().setJoker(false);
            nextPlayer();
        }

    }

    /**
     * Allows to get the playing players.
     * @return the list of playing players.
     */
    public List<Player> getPlayingPlayers() {
        return playingPlayers;
    }

    @Override
    public List<Player> getWinnerOfTheGame() {

        UltimateTicTacToe uttFinal = getUtt();
        List<Player> winner = new ArrayList<Player>(2);

        return parcoursList(uttFinal.getBoardParts(), winner);

    }

    /**
     * Allows to know if the game is over.
     * @return true if is over.
     */
    public boolean IsOver() {
        return isOver;
    }

    private List<Player> parcoursList(List<MiniTicTacToe> boardParts, List<Player> winner) {
        int joueurX = 0;
        int joueurO = 0;
        Player x = new Player("med", Symbol.X);
        Player o = new Player("nico", Symbol.O);
        if (playingPlayers.get(0).getSymbol().value == "X") {
            x = playingPlayers.get(0);
            o = playingPlayers.get(1);
        } else {
            x = playingPlayers.get(1);
            o = playingPlayers.get(0);
        }

        for (int i = 0; i < boardParts.size(); i++) {
            if (boardParts.get(i).sym != null) {
                if (boardParts.get(i).sym == Symbol.X) {
                    joueurX += 1;
                } else if (boardParts.get(i).getWinner().getValue() == "O") {
                    joueurO += 1;
                }
            }

        }

        if (joueurX < joueurO) {

            winner.add(o);
        } else if (joueurX > joueurO) {
            winner.add(x);
        } else {
            winner.add(x);
            winner.add(o);
        }
        return winner;
    }

    private Symbol RandomSym() {
        List<Symbol> listMarker = new ArrayList<>();
        listMarker.add(Symbol.X);
        listMarker.add(Symbol.O);
        int nb = (int) (Math.random() * 2) + 0;

        if (!playingPlayers.isEmpty() && playingPlayers.get(0).getSymbol() == listMarker.get(0)) {
            return listMarker.get(1);

        } else if (!playingPlayers.isEmpty() && playingPlayers.get(0).getSymbol() == listMarker.get(1)) {
            return listMarker.get(0);
        }
        return listMarker.get(nb);
    }

    
    private int getLoser() {
        for (int i = 0; i < playingPlayers.size(); i++) {
            if (!playingPlayers.get(i).getSymbol().getValue().equals(utt.getBigWinner().getValue())) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Allows to add victory score in database.
     * @throws DBException
     * @throws DTOException 
     */
    public void addScoreVictory() throws DBException, DTOException {

        PlayerDto winner = getPlayerInDataBase(getWinnerOfTheGame().get(0).getName());
        getDatabase().updateDb(new PlayerDto(winner.getPseudo(), winner.getNbWin() + 1, winner.getNbExAequo(), winner.getNbDefeat()));

    }

    /**
     * Allows to add Score to loser in db.
     * @throws DBException
     * @throws DTOException 
     */
    public void addScoreLoser() throws DBException, DTOException {

        PlayerDto winner = getPlayerInDataBase(getPlayingPlayers().get(getLoser()).getName());
        getDatabase().updateDb(new PlayerDto(winner.getPseudo(), winner.getNbWin(), winner.getNbExAequo(), winner.getNbDefeat() + 1));
    }

    /**
     * allows to add a victory for the no surrender.
     * @throws DBException
     * @throws DTOException 
     */
    public void addScoreVictoryForSurrend() throws DBException, DTOException {
        int cpt = 0;
        for (int i = 0; i < playingPlayers.size(); i++) {
            if (!playingPlayers.get(i).getSymbol().getValue().equals(currentPlayer.getSymbol().getValue())) {
                cpt = i;
            }
        }

        PlayerDto winner = getPlayerInDataBase(playingPlayers.get(cpt).getName());
        getDatabase().updateDb(new PlayerDto(winner.getPseudo(), winner.getNbWin() + 1, winner.getNbExAequo(), winner.getNbDefeat()));

    }

    /**
     * Allows to add defeat score for the surrender.
     * @throws DBException
     * @throws DTOException 
     */
    public void addScoreForDefeatSurrend() throws DBException, DTOException {

        PlayerDto winner = getPlayerInDataBase(currentPlayer.getName());
        getDatabase().updateDb(new PlayerDto(winner.getPseudo(), winner.getNbWin(), winner.getNbExAequo(), winner.getNbDefeat() + 1));
    }

    /**
     * Allows to add draw score.
     * @throws DBException
     * @throws DTOException 
     */
    public void addScoreDraw() throws DBException, DTOException {
        PlayerDto player1 = getPlayerInDataBase(getWinnerOfTheGame().get(0).getName());
        PlayerDto player2 = getPlayerInDataBase(getWinnerOfTheGame().get(1).getName());
        getDatabase().updateDb(new PlayerDto(player1.getPseudo(), player1.getNbWin(), player1.getNbExAequo() + 1, player1.getNbDefeat()));
        getDatabase().updateDb(new PlayerDto(player2.getPseudo(), player2.getNbWin(), player2.getNbExAequo() + 1, player2.getNbDefeat()));
    }

    private PlayerDto getPlayerInDataBase(String name) throws DBException, DTOException {
        for (PlayerDto player : getDatabase().getAllPlayers()) {
            if (player.getPseudo().equals(name)) {
                return player;
            }

        }
        return null;
    }

}
