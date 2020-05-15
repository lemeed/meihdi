
package atlg4.ultimate.g49262;

import atlg4.ultimate.g49262.exception.BusinessException;
import atlg4.ultimate.g49262.exception.DBException;
import atlg4.ultimate.g49262.exception.DTOException;
import atlg4.ultimate.g49262.model.Game;
import atlg4.ultimate.g49262.pers.PlayerDto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 49262 
 */
public class findPlayer {

    @FXML
    private TextField playerChoose;

    @FXML
    private Button ok;

    @FXML
    private Button home;

    Game game;
    public findPlayer(Game aGame) {
        game = aGame;
    }

    
    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        home.setOnAction((event) -> {
            home();
        });
       
        ok.setOnAction((event) -> {
            try {
                System.out.println("fez");
                ok();
            } catch (DBException ex) {
                
                Logger.getLogger(findPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DTOException ex) {
                
                Logger.getLogger(findPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BusinessException ex) {
                Logger.getLogger(findPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    private void ok() throws DBException, DTOException, BusinessException  {
        try {
           
            if (checkIfPlayerExist(playerChoose.getText())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("view/HistoController.fxml"));
                System.out.println("ef");
                HistoController con= new HistoController(game ,playerChoose.getText());
                loader.setController(con);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setMinHeight(450);
                stage.setMinWidth(650);
                stage.setScene(scene);
                stage.show();

                Stage st = (Stage) ok.getScene().getWindow();
                st.close();
            } else if (playerChoose.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Score");
                alert.setHeaderText("WARNING :");
                alert.setContentText("enter a pseudonym!");
                alert.showAndWait();
            } else if (!checkIfPlayerExist(playerChoose.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Score");
                alert.setHeaderText("WARNING :");
                alert.setContentText("this player does not exist!");
                alert.showAndWait();
            }

        } catch (IOException ex) {
        }
    }

    private void home() {
        home.setOnAction((event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Welcome.fxml"));
                WelcomeController c = new WelcomeController(game);
                loader.setController(c);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setMinHeight(450);
                stage.setMinWidth(650);
                stage.setScene(scene);
                stage.show();

                Stage st = (Stage) home.getScene().getWindow();
                st.close();

            } catch (IOException ex) {
            }
        });
    }

    private boolean checkIfPlayerExist(String name) throws DBException, DTOException  {
        for (PlayerDto player : game.getDatabase().getAllPlayers()) {
            
            if (player.getPseudo().equals(name)) {
                return true;
                
            }
        }
        return false;
    }

}
