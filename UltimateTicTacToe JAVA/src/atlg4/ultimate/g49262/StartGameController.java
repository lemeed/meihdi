/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atlg4.ultimate.g49262;

import atlg4.ultimate.g49262.model.Game;
import atlg4.ultimate.g49262.view.UTTController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Represent the start game controller.
 * @author meihdi El Amouri
 */
public class StartGameController {

    @FXML
    private ComboBox<String> player1;

    @FXML
    private ComboBox<String> player2;

    @FXML
    private Button back;

    @FXML
    private Button log_in;

    @FXML
    private Button startGame;

    private Game game;

    StartGameController(Game aGame) {
        game = aGame;

    }

    
    @FXML
    public void initialize() {
       
        player1.setItems(game.getDatabase().getAllRegistredPlayer());
        player1.setEditable(true);
        player2.setItems(game.getDatabase().getAllRegistredPlayer());
        player2.setEditable(true);
        
       

         {
            startGame.setOnAction((event) -> {
                
                
                if (player1.getSelectionModel().getSelectedItem() != null && player2.getSelectionModel().getSelectedItem() != null
                         && !player1.getSelectionModel().getSelectedItem().equals( player2.getSelectionModel().getSelectedItem())) {

                    try {

                        FXMLLoader l = new FXMLLoader(getClass().getResource("view/UTT.fxml"));
                        Game g = new Game();
                        g.addPlayer(player1.getValue());
                        g.addPlayer(player2.getValue());
                        UTTController w = new UTTController(g);
                        l.setController(w);
                        Parent root = l.load();
                        Scene scene = new Scene(root);
                        Stage s = new Stage();
                        
                        s.setScene(scene);
                        s.show();

                        Stage ss = (Stage) back.getScene().getWindow();
                        ss.close();
                    } catch (IOException ex) {

                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game is not possible");
                alert.setHeaderText("WARNING :");
                alert.setContentText("Please select 2 different player");
                alert.showAndWait();
                }

            });
        }

        back.setOnAction((event) -> {
            back();
        });

    }

    /**
     * Allows to init the backbutton.
     */
    private void back() {
        try {

            
            FXMLLoader l = new FXMLLoader(getClass().getResource("view/Welcome.fxml"));
            WelcomeController w = new WelcomeController(game);
            l.setController(w);
            Parent root = l.load();
            Scene scene = new Scene(root);
            Stage s = new Stage();
            s.setMinHeight(450);
            s.setMaxWidth(650);
            s.setScene(scene);
            s.show();

            Stage ss = (Stage) back.getScene().getWindow();
            ss.close();

        } catch (IOException e) {

        }
    }

    
    /**
     * Allows to get the first player.
     * @return the first player.
     */
    public ComboBox<String> getPlayer1() {
        return player1;
    }

    /**
     * Allows to get the second player.
     * @return the second player.
     */
    public ComboBox<String> getPlayer2() {
        return player2;
    }

    
}
