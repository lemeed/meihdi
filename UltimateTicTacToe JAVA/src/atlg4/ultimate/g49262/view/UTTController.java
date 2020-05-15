/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262.view;

import atlg4.ultimate.g49262.model.Game;
import atlg4.composant.g49262.view.MyTicTacToe;
import atlg4.ultimate.g49262.EndGame;
import atlg4.ultimate.g49262.exception.DBException;
import atlg4.ultimate.g49262.exception.DTOException;
import atlg4.ultimate.g49262.model.MiniTicTacToe;
import atlg4.ultimate.g49262.model.Symbol;
import atlg4.ultimate.g49262.model.UltimateTicTacToe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author meihdi El Amouri
 */
public class UTTController {

    private Game game;

    private int miniTTT = 0;
    private int UTT = 0;

    @FXML
    private MyTicTacToe tt_01 = new MyTicTacToe();

    @FXML
    private MyTicTacToe tt_02 = new MyTicTacToe();

    @FXML
    private MyTicTacToe tt_03 = new MyTicTacToe();

    @FXML
    private MyTicTacToe tt_04 = new MyTicTacToe();

    @FXML
    private MyTicTacToe tt_05 = new MyTicTacToe();

    @FXML
    private MyTicTacToe tt_06 = new MyTicTacToe();

    @FXML
    private MyTicTacToe tt_07 = new MyTicTacToe();

    @FXML
    private MyTicTacToe tt_08 = new MyTicTacToe();

    @FXML
    private MyTicTacToe tt_09 = new MyTicTacToe();

    @FXML
    private Label bigWinner = new Label("");

    @FXML
    private TextField currentPlayer = new TextField("");

    @FXML
    private Button surrend = new Button("");

    private ArrayList<MyTicTacToe> list = new ArrayList<MyTicTacToe>(9);

    public UTTController(Game game) {

        this.game = game;

    }

    /**
     * Allows to initialize the list of mini tic tac toe.
     */
    public void initList() {
        list.add(tt_01);
        list.add(tt_02);
        list.add(tt_03);
        list.add(tt_04);
        list.add(tt_05);
        list.add(tt_06);
        list.add(tt_07);
        list.add(tt_08);
        list.add(tt_09);

    }

    /**
     * Allows to initialize my fxml.
     */
    @FXML
    public void initialize() {
        
        initList();

        currentPlayer.setText(game.getCurrentPlayer().getName());
        int cpt = 0;

        surrend.setOnAction((ActionEvent event) -> {
            try {
                game.addScoreForDefeatSurrend();
                game.addScoreVictoryForSurrend();
                surrend();
                printEnd();
            } catch (DBException | DTOException | IOException ex) {
                Logger.getLogger(UTTController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        for (Node node : list) {

            if (cpt <= 8) {
                for (Node node1 : list.get(cpt).getList()) {

                    ((Button) node1).setOnAction((ActionEvent event) -> {
                        game.isGameOver();
                        MiniTicTacToe temp = game.getUtt().getBoardParts().get(UTT);
                        positionLive(((Button) node1));

                        if (temp.isFinished()) {
                            game.getUtt().setLastMoveBoardPart(game.getUtt().getBoardParts().get(UTT));
                        }
                        

                        game.putSymbol(UTT, miniTTT);

                        checkMiniTTTWin();
                        checkBigUTT();

                        refresh();

                        if (game.IsOver()) {
                            try {
                                gameIsOver();
                                printEnd();
                            } catch (DBException ex) {
                                Logger.getLogger(UTTController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DTOException ex) {
                                Logger.getLogger(UTTController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(UTTController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        currentPlayer.setText(game.getCurrentPlayer().getSymbol().getValue() + "- " + game.getCurrentPlayer().getName());

                    });
                }

                cpt++;

            }

        }

    }

    /**
     * Allows to know where is the position that the player wants to play.
     *
     * @param button the button that he selected.
     */
    private void positionLive(Button button) {
        boolean find = false;
        int cpt = 0;
        int cpt1 = 0;
        for (Node node : list) {

            cpt = 0;
            for (Node node1 : list.get(cpt1).getList()) {

                if (button == ((Button) node1)) {
                    find = true;
                    break;
                }
                cpt++;
            }

            if (find) {
                break;
            }
            cpt1++;

        }
        UTT = cpt1;
        miniTTT = cpt;
    }

    /**
     * Allows to refresh the board.
     */
    private void refresh() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                Button buttonMod = ((Button) list.get(i).getList().get(j));
                MiniTicTacToe st = (MiniTicTacToe) game.getUtt().getBoardParts().get(i);
                if (st.getSquares().get(j).getSymbol() == null) {
                    String nule = "";

                    buttonMod.setText(nule);
                } else {
                    String sym = st.getSquares().get(j).getSymbol().getValue();

                    buttonMod.setText(sym);
                    if (sym == "O") {
                        buttonMod.setTextFill(Color.web("#FF1951"));
                    } else {
                        buttonMod.setTextFill(Color.web("#1C53CC"));
                    }

                }

            }
        }
    }

    /**
     * Allows to check if a mini tic tac toe is finish.
     */
    public void checkMiniTTTWin() {
        for (int i = 0; i < 9; i++) {
            MiniTicTacToe st = (MiniTicTacToe) game.getUtt().getBoardParts().get(i);

            if (st.isFinished() && st.getSymbol() != null) {
                System.out.println(st.getSymbol());
                st.setSymbol(st.getWinner());

                setWinner(st.getWinner().getValue(), i);
            } else if (st.isFinished()) {
                list.get(i).setDisable(true);
            }

        }
    }

    /**
     * Allows to refresh a ultimate tic tac toe finish.
     */
    public void checkBigUTT() {
        for (int i = 0; i < 9; i++) {
            UltimateTicTacToe st = (UltimateTicTacToe) game.getUtt();
            if (st.isFinished()) {

                setBigWinner(st.getBigWinner());
                System.out.println(game.getWinnerOfTheGame().get(0));
            }
        }
    }

    /**
     * Allows to set a winner in a mini tic tac toe.
     *
     * @param r the value of player symbol.
     * @param i the index of the list for mini ttt.
     */
    public void setWinner(String r, int i) {
        if (r != "" && r != null) {
            list.get(i).setWinner(r);
        }

    }

    /**
     * Allows to set the symbol of the big winner of the game.
     *
     * @param bigWinner the winner of the game.
     */
    private void setBigWinner(Symbol bigWinner) {
        this.bigWinner.setText(game.getUtt().getWinner().getValue());
        if (bigWinner.getValue() == "O") {
            this.bigWinner.setTextFill(Color.web("#FF1951"));
        } else {
            this.bigWinner.setTextFill(Color.web("#1C53CC"));
        }
    }

    private void gameIsOver() throws DBException, DTOException {
        if (game.getWinnerOfTheGame().size() > 1) {
            game.addScoreDraw();
        } else {
            game.addScoreVictory();
            game.addScoreLoser();
        }
    }

    private void printEnd() throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("EndGame.fxml"));

        EndGame w = new EndGame(game);
        l.setController(w);
        Parent root = l.load();
        Scene scene = new Scene(root);
        Stage s = new Stage();

        s.setScene(scene);
        s.show();

        Stage ss = (Stage) surrend.getScene().getWindow();
        ss.close();
    }

    
    private void surrend() {
        game.getPlayingPlayers().remove(game.getCurrentPlayer());
        game.setIsOver(true);

    }
}
